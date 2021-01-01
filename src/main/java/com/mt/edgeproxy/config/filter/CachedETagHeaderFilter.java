package com.mt.edgeproxy.config.filter;

import com.mt.edgeproxy.config.ETagStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
public class CachedETagHeaderFilter extends ShallowEtagHeaderFilter {
    @Autowired
    private ETagStore eTagStore;

    @Override
    protected String generateETagHeaderValue(InputStream inputStream, boolean isWeak) throws IOException {
        Optional<HttpServletRequest> httpServletRequest = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
        String queryString = httpServletRequest.get().getQueryString();
        String uri = httpServletRequest.get().getRequestURI();

        String eTagHeaderValue = super.generateETagHeaderValue(inputStream, isWeak);

        eTagStore.setETags(uri, queryString, eTagHeaderValue);
        return eTagHeaderValue;
    }

}
