package com.mt.edgeproxy.config.filter;

import com.mt.edgeproxy.config.ETagStore;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class HttpCachedETagFilter extends ZuulFilter {
    @Autowired
    private ETagStore eTagStore;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Value("${app.product-svc.product.name}")
    private String productName;
    @Value("${app.product-svc.skus.name}")
    private String skuName;

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
        String header = httpServletRequestWrapper.getRequest().getHeader(HttpHeaders.IF_NONE_MATCH);
        String uri = httpServletRequestWrapper.getRequest().getRequestURI();
        String queryString = httpServletRequestWrapper.getRequest().getQueryString();
        String eTags = eTagStore.getETags(uri, queryString);//save retrieved etag, as this operation is not idempotent
        if (request.getMethod().equalsIgnoreCase(HttpMethod.GET.name())
                && eTags != null
                && eTags.equals(header)
                && !request.getRequestURI().contains("changes")
        ) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.NOT_MODIFIED.value());
        } else {
            if (!request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name()) && !request.getMethod().equalsIgnoreCase(HttpMethod.GET.name())) {
                // POST PUT PATCH DELETE
                if (request.getRequestURI().contains(productName)) {
                    // invalid sku cache when product change
                    eTagStore.clearResourceETag(skuName);
                }
                eTagStore.clearResourceETag(request.getRequestURI());
            }
        }
        return null;
    }
}
