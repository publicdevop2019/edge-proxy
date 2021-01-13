package com.mt.edgeproxy.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.edgeproxy.domain.DomainRegistry;
import com.mt.edgeproxy.domain.Endpoint;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.util.SimpleMethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
@Component
public class EndpointFilter extends ZuulFilter {

    private static Method triggerCheckMethod;
    private static final SpelExpressionParser parser;
    public static final String EDGE_PROXY_UNAUTHORIZED_ACCESS = "internal forward check failed";
    public static final String EXCHANGE_RELOAD_EP_CACHE = "reloadEpCache";
    private Set<Endpoint> cached;

    static {
        try {
            triggerCheckMethod = SecurityObject.class.getMethod("triggerCheck");
        } catch (NoSuchMethodException e) {
        }
        parser = new SpelExpressionParser();
    }

    @Autowired
    ObjectMapper mapper;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


    @PostConstruct
    private void load() {
        cached = DomainRegistry.roadEndpointService().loadAllEndpoints();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("mt_global_exchange", "topic");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, "mt_global_exchange", "oauth.external.system");
            channel.queueBind(queueName, "mt_global_exchange", "oauth.external.endpoint");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                log.debug("start refresh cached endpoints");
                cached = DomainRegistry.roadEndpointService().loadAllEndpoints();
                log.debug("cached endpoints refreshed");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            log.error("error in mq", e);
        }
    }


    @Override
    public Object run() throws ZuulException {
        long startTime = System.currentTimeMillis();
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
        String requestURI = httpServletRequestWrapper.getRequestURI();
        String method = httpServletRequestWrapper.getMethod();
        String authHeader = httpServletRequestWrapper.getHeader("authorization");
        if (requestURI.contains("/oauth/token") || requestURI.contains("/oauth/token_key")) {
            /**
             * permit all token endpoints,
             * we could apply security to token endpoint as well, however we don't want to increase DB query
             */
        } else if (authHeader == null || !authHeader.contains("Bearer") || requestURI.contains("/public")) {
            List<Endpoint> collect1 = cached.stream().filter(e -> e.getExpression() == null).filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());
            if (collect1.size() == 0) {
                /** un-registered public endpoints */
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                request.setAttribute(EDGE_PROXY_UNAUTHORIZED_ACCESS, Boolean.TRUE);
                return null;
            }

        } else if (authHeader.contains("Bearer")) {
            /**
             * check endpoint url, method first then check resourceId and security rule
             */
            Jwt jwt = JwtHelper.decode(authHeader.replace("Bearer ", ""));
            Map<String, Object> claims;
            try {
                claims = mapper.readValue(jwt.getClaims(), Map.class);
            } catch (IOException e) {
                claims = new HashMap<>();
                /** this block is purposely left blank */
            }
            ArrayList<String> resourceIds = (ArrayList<String>) claims.get("aud");
            SecurityObject securityObject = new SecurityObject();

            OAuth2MethodSecurityExpressionHandler expressionHandler = new OAuth2MethodSecurityExpressionHandler();
            EvaluationContext evaluationContext = expressionHandler.createEvaluationContext(SecurityContextHolder.getContext().getAuthentication(), new SimpleMethodInvocation(securityObject, triggerCheckMethod));

            /**
             * fetch security profile
             */
            if (resourceIds == null || resourceIds.isEmpty()) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                request.setAttribute(EDGE_PROXY_UNAUTHORIZED_ACCESS, Boolean.TRUE);
                return null;
            }
            List<Endpoint> collect = cached.stream().filter(e -> resourceIds.contains(e.getResourceId())).collect(Collectors.toList());
            /**
             * fetch security rule by endpoint & method
             */
            List<Endpoint> collect1 = collect.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());

            Optional<Endpoint> mostSpecificSecurityProfile = EndpointMatcher.getMostSpecificSecurityProfile(collect1);

            boolean passed = false;
            if (mostSpecificSecurityProfile.isPresent()) {
                passed = ExpressionUtils.evaluateAsBoolean(parser.parseExpression(mostSpecificSecurityProfile.get().getExpression()), evaluationContext);
            }

            if (mostSpecificSecurityProfile.isEmpty() || !passed) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                request.setAttribute(EDGE_PROXY_UNAUTHORIZED_ACCESS, Boolean.TRUE);
                return null;
            }
            log.info("elapse in endpoint filter::" + (System.currentTimeMillis() - startTime));
        } else {
            /** un-registered endpoints */
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
            request.setAttribute(EDGE_PROXY_UNAUTHORIZED_ACCESS, Boolean.TRUE);
            return null;
        }
        return null;
    }

    private static class SecurityObject {
        public void triggerCheck() { /*NOP*/ }
    }


    public static class EndpointMatcher {

        public static Optional<Endpoint> getMostSpecificSecurityProfile(List<Endpoint> collect1) {
            if (collect1.size() == 1)
                return Optional.of(collect1.get(0));
            List<Endpoint> exactMatch = collect1.stream().filter(e -> !e.getPath().contains("/**")).collect(Collectors.toList());
            if (exactMatch.size() == 1)
                return Optional.of(exactMatch.get(0));
            List<Endpoint> collect2 = collect1.stream().filter(e -> !e.getPath().endsWith("/**")).collect(Collectors.toList());
            if (collect2.size() == 1)
                return Optional.of(collect2.get(0));
            return Optional.empty();
        }
    }
}
