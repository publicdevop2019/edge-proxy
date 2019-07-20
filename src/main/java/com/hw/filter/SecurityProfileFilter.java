package com.hw.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.entity.SecurityProfile;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
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
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class SecurityProfileFilter extends ZuulFilter {

    @Autowired
    ObjectMapper mapper;

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
        SecurityProfile securityProfile = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
                "oauth2-id", "/api/v1/clients", HttpMethod.GET, 0L);
        SecurityProfile securityProfile2 = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
                "oauth2-id", "/api/v1/client", HttpMethod.POST, 1L);
        SecurityProfile securityProfile3 = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
                "oauth2-id", "/api/v1/client/**", HttpMethod.PUT, 2L);
        SecurityProfile securityProfile4 = new SecurityProfile("hasRole('ROLE_ROOT') and #oauth2.hasScope('trust') and #oauth2.isUser()",
                "oauth2-id", "/api/v1/client/**", HttpMethod.DELETE, 3L);
        ArrayList<SecurityProfile> securityProfiles = new ArrayList<>(4);
        securityProfiles.add(securityProfile);
        securityProfiles.add(securityProfile2);
        securityProfiles.add(securityProfile3);
        securityProfiles.add(securityProfile4);

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
        if (securityProfiles.stream().anyMatch(e -> antPathMatcher.match(e.getEndpoint(), requestURI) && method.equals(e.getMethod().toString()))) {
            if (authHeader != null && authHeader.contains("Bearer")) {
                Jwt jwt = JwtHelper.decode(authHeader.replace("Bearer ", ""));
                try {
                    Map claims = mapper.readValue(jwt.getClaims(), Map.class);
                    Integer iat = (Integer) claims.get("iat");
                    String userName = (String) claims.get("user_name");
                    String clientId = (String) claims.get("client_id");
                    ArrayList<String> resourceIds = (ArrayList<String>) claims.get("aud");
                    ArrayList<String> scopes = (ArrayList<String>) claims.get("scope");
                    ArrayList<String> authorities = (ArrayList<String>) claims.get("authorities");
                    SecurityObject securityObject = new SecurityObject();
                    OAuth2MethodSecurityExpressionHandler expressionHandler = new OAuth2MethodSecurityExpressionHandler();
                    EvaluationContext evaluationContext = expressionHandler.createEvaluationContext(SecurityContextHolder.getContext().getAuthentication(), new SimpleMethodInvocation(securityObject, triggerCheckMethod));
                    boolean checkResult = ExpressionUtils.evaluateAsBoolean(parser.parseExpression(securityProfile.getExpression()), evaluationContext);
                    if (checkResult && resourceIds.contains(securityProfile.getResourceID())) {
                        return null;
                    } else {
                        ctx.setSendZuulResponse(false);
                        ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                    }
                } catch (IOException e) {

                    /**
                     * this block is purposely left blank
                     */
                }
            }
        } else {
            /**
             * @todo if profile not found, request should be blocked
             */
            return null;
        }

        return null;
    }
}
