package com.mt.edgeproxy.infrastructure;

import com.google.json.JsonSanitizer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

@Component
public class RequestJsonSanitizerFilter extends ZuulFilter {


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
        RequestContext ctx = getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        return request.getContentType() != null && request.getContentType().contains("application/json");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = getCurrentContext();
        try {
            InputStream in = (InputStream) ctx.get("requestEntity");
            if (in == null) {
                in = ctx.getRequest().getInputStream();
            }
            String body = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
            String afterSanitize = JsonSanitizer.sanitize(body);
            byte[] bytes = afterSanitize.getBytes(StandardCharsets.UTF_8);
            ctx.setRequest(new HttpServletRequestWrapper(getCurrentContext().getRequest()) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(bytes);
                }

                @Override
                public int getContentLength() {
                    return bytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return bytes.length;
                }
            });
        } catch (IOException e) {
            rethrowRuntimeException(e);
        }
        return null;
    }
}
