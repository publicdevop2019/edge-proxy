package com.hw.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.endpoint.BizEndpointRepo;
import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.config.SecurityProfileMatcher;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class SecurityProfileFilter extends ZuulFilter {

    private static Method triggerCheckMethod;
    private static final SpelExpressionParser parser;
    public static final String EDGE_PROXY_UNAUTHORIZED_ACCESS = "internal forward check failed";

    static {
        try {
            triggerCheckMethod = SecurityObject.class.getMethod("triggerCheck");
        } catch (NoSuchMethodException e) {
        }
        parser = new SpelExpressionParser();
    }

    @Autowired
    ObjectMapper mapper;

    @Autowired
    BizEndpointRepo securityProfileRepo;

    @Autowired
    AntPathMatcher antPathMatcher;

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

    @Override
    public Object run() throws ZuulException {
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
            List<BizEndpoint> publicEpsProfiles = securityProfileRepo.findAll().stream().filter(e -> e.getExpression() == null).collect(Collectors.toList());
            List<BizEndpoint> collect1 = publicEpsProfiles.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());
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
            List<BizEndpoint> collect = resourceIds.stream().map(e -> securityProfileRepo.findByResourceId(e)).flatMap(Collection::stream).collect(Collectors.toList());

            /**
             * fetch security rule by endpoint & method
             */
            List<BizEndpoint> collect1 = collect.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());

            Optional<BizEndpoint> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(collect1);

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
}
