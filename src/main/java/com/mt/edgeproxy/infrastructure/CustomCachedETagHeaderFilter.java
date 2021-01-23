//package com.mt.edgeproxy.infrastructure;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.ShallowEtagHeaderFilter;
//
//import java.io.IOException;
//import java.io.InputStream;
//
////@Component
//public class CustomCachedETagHeaderFilter extends ShallowEtagHeaderFilter {
//    @Autowired
//    private ETagStore eTagStore;
//
//    @Override
//    protected String generateETagHeaderValue(InputStream inputStream, boolean isWeak) throws IOException {
////        Optional<HttpServletRequest> httpServletRequest = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
////                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
////                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
////                .map(ServletRequestAttributes::getRequest);
////        String queryString = httpServletRequest.get().getQueryString();
////        String uri = httpServletRequest.get().getRequestURI();
////
////        String eTagHeaderValue = super.generateETagHeaderValue(inputStream, isWeak);
////
////        eTagStore.setETags(uri, queryString, eTagHeaderValue);
////        return eTagHeaderValue;
//        return null;
//    }
//
//}
