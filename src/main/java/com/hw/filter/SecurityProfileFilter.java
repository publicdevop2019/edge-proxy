package com.hw.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.entity.SecurityProfile;
import com.hw.repo.SecurityProfileRepo;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class SecurityProfileFilter extends ZuulFilter {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    SecurityProfileRepo securityProfileRepo;

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

    private static class SecurityObject {
        public void triggerCheck() { /*NOP*/ }
    }

    private static Method triggerCheckMethod;
    private static SpelExpressionParser parser;

    static {
        try {
            triggerCheckMethod = SecurityObject.class.getMethod("triggerCheck");
        } catch (NoSuchMethodException e) {
//            logger.error(e);
        }
        parser = new SpelExpressionParser();
    }

    @Override
    public Object run() throws ZuulException {
        /**
         * poc
         */
//        SecurityProfile securityProfile = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
//                "oauth2-id", "/api/v1/clients", HttpMethod.GET.toString(), 0L);
//        SecurityProfile securityProfile2 = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
//                "oauth2-id", "/api/v1/client", HttpMethod.POST.toString(), 1L);
//        SecurityProfile securityProfile3 = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
//                "oauth2-id", "/api/v1/client/**", HttpMethod.PUT.toString(), 2L);
//        SecurityProfile securityProfile4 = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
//                "oauth2-id", "/api/v1/client/**", HttpMethod.DELETE.toString(), 3L);
//        ArrayList<SecurityProfile> securityProfiles = new ArrayList<>(4);
//        securityProfiles.add(securityProfile);
//        securityProfiles.add(securityProfile2);
//        securityProfiles.add(securityProfile3);
//        securityProfiles.add(securityProfile4);

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
        String requestURI = httpServletRequestWrapper.getRequestURI();
        String method = httpServletRequestWrapper.getMethod();
        String authHeader = httpServletRequestWrapper.getHeader("authorization");
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        /**
         * check endpoint url, method first then check resourceId and security rule
         */
        if (authHeader != null && authHeader.contains("Bearer")) {
            Jwt jwt = JwtHelper.decode(authHeader.replace("Bearer ", ""));
            Map claims = null;
            try {
                claims = mapper.readValue(jwt.getClaims(), Map.class);
            } catch (IOException e) {
                /** this block is purposely left blank */
            }
            ArrayList<String> resourceIds = (ArrayList<String>) claims.get("aud");
            SecurityObject securityObject = new SecurityObject();

            OAuth2MethodSecurityExpressionHandler expressionHandler = new OAuth2MethodSecurityExpressionHandler();
            EvaluationContext evaluationContext = expressionHandler.createEvaluationContext(SecurityContextHolder.getContext().getAuthentication(), new SimpleMethodInvocation(securityObject, triggerCheckMethod));

            /**
             * fetch security profile
             */
            List<SecurityProfile> collect = resourceIds.stream().map(e -> securityProfileRepo.findByResourceID(e)).flatMap(Collection::stream).collect(Collectors.toList());

            /**
             * fetch security rule by endpoint & method
             */
            List<SecurityProfile> collect1 = collect.stream().filter(e -> antPathMatcher.match(e.getEndpoint(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());
            boolean b = collect1.stream().allMatch(e -> ExpressionUtils.evaluateAsBoolean(parser.parseExpression(e.getExpression()), evaluationContext));

            if (b) {
                return null;
            } else {
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                return null;
            }
        } else {
            /**
             * for api without bearer token, can be token endpoints or un-registered endpoints
             */
            return null;
        }
    }
}
