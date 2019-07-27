package com.hw.filter;

import com.hw.entity.SecurityProfile;
import com.hw.repo.SecurityProfileRepo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.hw.clazz.Constant.EDGE_PROXY_UNMAPPED_ROUTE;

@Component
public class DynamicRouteFilter extends ZuulFilter {

    @Autowired
    SecurityProfileRepo securityProfileRepo;

    @Override
    public String filterType() {
        return "pre";
    }


    @Override
    public int filterOrder() {
        return 101;
    }


    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestURL = ctx.getRequest().getRequestURL().toString();
        return !(requestURL.contains("proxy") || requestURL.contains("token"));
    }

    @Autowired
    AntPathMatcher antPathMatcher;

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestURI = ctx.getRequest().getRequestURI();
        String method = ctx.getRequest().getMethod();
        List<SecurityProfile> all = securityProfileRepo.findAll();
        Optional<SecurityProfile> first = all.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && e.getMethod().equals(method)).findFirst();
        /**
         * modify url
         */
        if (first.isPresent()) {
            SecurityProfile securityProfile = first.get();
            String replace = securityProfile.getPath().replace("/**", "");
            String replace1 = requestURI.replace(replace, "");
            ctx.set("requestURI", replace1);
            try {
                ctx.setRouteHost(new URL(securityProfile.getUrl()));
            } catch (MalformedURLException e) {
                /**
                 * this block is purposely left blank
                 */
            }
        } else {
            ctx.getRequest().setAttribute(EDGE_PROXY_UNMAPPED_ROUTE, Boolean.TRUE);
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return null;
    }
}
