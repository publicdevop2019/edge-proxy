package com.hw.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.endpoint.AppBizEndpointApplicationService;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;
import com.hw.config.SecurityProfileMatcher;
import com.hw.shared.RecordElapseTime;
import com.hw.shared.sql.SumPagedRep;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.hw.aggregate.endpoint.model.BizEndpoint.ENTITY_EXPRESSION;
import static com.hw.aggregate.endpoint.model.BizEndpoint.ENTITY_RESOURCE_ID;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
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
    AppBizEndpointApplicationService appBizEndpointApplicationService;

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
    @RecordElapseTime
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
            List<AppBizEndpointCardRep> all = getAll(ENTITY_EXPRESSION + ":null");
            List<AppBizEndpointCardRep> collect1 = all.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());
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
            List<AppBizEndpointCardRep> collect = resourceIds.stream().map(e -> getAll(ENTITY_RESOURCE_ID + ":" + e)).flatMap(Collection::stream).collect(Collectors.toList());

            /**
             * fetch security rule by endpoint & method
             */
            List<AppBizEndpointCardRep> collect1 = collect.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());

            Optional<AppBizEndpointCardRep> mostSpecificSecurityProfile = SecurityProfileMatcher.getMostSpecificSecurityProfile(collect1);

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
            log.info("elapse in security filter::" + (System.currentTimeMillis() - startTime));
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

    private List<AppBizEndpointCardRep> getAll(String query) {
        int pageNum = 0;
        SumPagedRep<AppBizEndpointCardRep> appBizEndpointCardRepSumPagedRep = appBizEndpointApplicationService.readByQuery(query, "num:" + pageNum, null);
        if (appBizEndpointCardRepSumPagedRep.getData().size() == 0)
            return new ArrayList<>();
        double l = (double) appBizEndpointCardRepSumPagedRep.getTotalItemCount() / appBizEndpointCardRepSumPagedRep.getData().size();
        double ceil = Math.ceil(l);
        int i = BigDecimal.valueOf(ceil).intValue();
        List<AppBizEndpointCardRep> data = new ArrayList<>(appBizEndpointCardRepSumPagedRep.getData());
        for (int a = 1; a < i; a++) {
            data.addAll(appBizEndpointApplicationService.readByQuery(query, "num:" + a, null).getData());
        }
        return data;
    }
}
