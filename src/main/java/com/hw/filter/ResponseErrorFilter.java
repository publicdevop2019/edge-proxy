package com.hw.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 * suppress 500, 503 response body with null
 * this prevent unwanted system details from leaking outside
 */
@Component
public class ResponseErrorFilter extends ZuulFilter {
    private static final Integer[] errorCodes = {500, 503};
    private static final List<Integer> integers = Arrays.asList(errorCodes);

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse servletResponse = context.getResponse();
        if (integers.contains(servletResponse.getStatus())){
            context.setResponseBody("");
        }
        return null;
    }
}
