package com.hw.config.filter;

import com.hw.config.ETagStore;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class HttpCachedETagFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Autowired
    private ETagStore eTagStore;

    @Override
    public int filterOrder() {
        return 0;
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
        String header = httpServletRequestWrapper.getRequest().getHeader(HttpHeaders.IF_NONE_MATCH);
        String uri = httpServletRequestWrapper.getRequest().getRequestURI();
        String queryString = httpServletRequestWrapper.getRequest().getQueryString();
        String fullUri = uri + "?" + queryString;
        if (request.getMethod().equalsIgnoreCase(HttpMethod.GET.name()) && eTagStore.getETags(fullUri) != null && eTagStore.getETags(fullUri).equals(header)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.NOT_MODIFIED.value());
        } else {
            if (!request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
                // POST PUT PATCH DELETE
                String[] split = request.getRequestURI().split("/");
                String s = split[1] + "/" + split[2];
                eTagStore.clearResourceETag(s);
            }
        }
        return null;
    }
}
