package com.mt.proxy.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EndpointService {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private Set<Endpoint> cached;
    @Autowired
    private CsrfService csrfService;
    public static Optional<Endpoint> getMostSpecificSecurityProfile(List<Endpoint> collect1) {
        if (collect1.size() == 1)
            return Optional.of(collect1.get(0));
        List<Endpoint> exactMatch = collect1.stream().filter(e -> !e.getPath().contains("/**")).collect(Collectors.toList());
        if (exactMatch.size() == 1)
            return Optional.of(exactMatch.get(0));
        List<Endpoint> collect2 = collect1.stream().filter(e -> !e.getPath().endsWith("/**")).collect(Collectors.toList());
        if (collect2.size() == 1)
            return Optional.of(collect2.get(0));
        return Optional.empty();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadAllEndpoints() {
        cached = DomainRegistry.retrieveEndpointService().loadAllEndpoints();
        csrfService.refreshCsrfConfig(cached);
    }

    public AntPathMatcher getPathMater() {
        return antPathMatcher;
    }
    public boolean checkAccess(String requestURI, String method, @Nullable String authHeader, boolean webSocket) throws ParseException {
        if (webSocket) {
            if (authHeader == null) {
                log.debug("return 403 due to empty auth info");
                return false;
            }
            if (!DomainRegistry.jwtService().verify(authHeader.replace("Bearer ", ""))) {
                log.debug("return 403 due to jwt failed for verification");
                return false;
            } else {
                //check roles
                return checkAccessByRole(requestURI, method, authHeader, true);
            }
        }
        if (requestURI.contains("/oauth/token") || requestURI.contains("/oauth/token_key")) {
            //permit all token endpoints,
            return true;
        } else if (authHeader == null || !authHeader.contains("Bearer") || requestURI.contains("/public")) {
            List<Endpoint> collect1 = cached.stream().filter(e -> !e.isSecured()).filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());
            if (collect1.size() == 0) {
                log.debug("return 403 due to un-registered public endpoints or no authentication info found");
                return false;
            } else {
                return true;
            }
        } else if (authHeader.contains("Bearer")) {
            return checkAccessByRole(requestURI, method, authHeader, false);
        } else {
            log.debug("return 403 due to un-registered endpoints");
            return false;
        }
    }
    public boolean checkCsrf(String requestURI, String method, boolean webSocket){

        return false;
    }
    private boolean checkAccessByRole(String requestURI, String method, String authHeader, boolean websocket) throws ParseException {
        //check endpoint url, method first then check resourceId and security rule
        String jwtRaw = authHeader.replace("Bearer ", "");
        Set<String> resourceIds = DomainRegistry.jwtService().getResourceIds(jwtRaw);

        //fetch security profile
        if (resourceIds == null || resourceIds.isEmpty()) {
            log.debug("return 403 due to resourceIds is null or empty");
            return false;
        }
        List<Endpoint> collect = cached.stream().filter(e -> resourceIds.contains(e.getResourceId())).collect(Collectors.toList());
        //fetch security rule by endpoint & method
        List<Endpoint> collect1;
        if (websocket) {
            collect1 = collect.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && e.isWebsocket()).collect(Collectors.toList());
        } else {
            collect1 = collect.stream().filter(e -> antPathMatcher.match(e.getPath(), requestURI) && method.equals(e.getMethod())).collect(Collectors.toList());
        }

        Optional<Endpoint> mostSpecificSecurityProfile = getMostSpecificSecurityProfile(collect1);
        boolean passed;
        if (mostSpecificSecurityProfile.isPresent()) {
            passed = mostSpecificSecurityProfile.get().allowAccess(jwtRaw);
        } else {
            log.debug("return 403 due to endpoint not found");
            return false;
        }
        if (!passed) {
            log.debug("return 403 due to not pass check");
            return false;
        } else {
            return true;
        }
    }
}
