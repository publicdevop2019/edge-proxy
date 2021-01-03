package com.mt.edgeproxy.infrastructure;

import com.google.json.JsonSanitizer;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class ResponseJsonSanitizerFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = getCurrentContext();
        List<Pair<String, String>> zuulResponseHeaders = ctx.getZuulResponseHeaders();
        return zuulResponseHeaders.stream().anyMatch(e -> e.second() != null && e.second().contains("application/json"));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = context.getResponseDataStream()) {

            if (responseDataStream == null) {
                return null;
            }
            String s = StreamUtils.copyToString(responseDataStream, StandardCharsets.UTF_8);
            String s2 = s.replace("<", "&lt;");
            String s3 = s2.replace(">", "&gt;");
            String afterSanitize = JsonSanitizer.sanitize(s3);
            context.setResponseBody(afterSanitize);
        } catch (Exception e) {
            throw new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return null;
    }
}
