package com.mt.edgeproxy.domain;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class JwtService {

    public Set<String> getResourceIds(String jwtRaw) {
        return getClaims(jwtRaw, "aud");
    }

    public Set<String> getScopes(String jwtRaw) {
        return getClaims(jwtRaw, "scope");
    }

    public Set<String> getRoles(String jwtRaw) {
        return getClaims(jwtRaw, "authorities");
    }

    public Long getIssueAt(String jwtRaw) {
        return Long.parseLong(getClaims(jwtRaw, "iat").stream().findAny().get());
    }

    public String getUserId(String jwtRaw) {
        if (isUser(jwtRaw))
            return getClaims(jwtRaw, "uid").stream().findAny().get();
        return null;
    }

    public String getClientId(String jwtRaw) {
        return getClaims(jwtRaw, "client_id").stream().findAny().get();
    }

    public boolean isUser(String jwtRaw) {
        Set<String> uid = getClaims(jwtRaw, "uid");
        return !uid.isEmpty() && !uid.contains(null);
    }

    private Set<String> getClaims(String jwtRaw, String field) {
        JWT jwt;
        try {
            jwt = JWTParser.parse(jwtRaw);
        } catch (ParseException e) {
            log.error("error during parse jwt", e);
            throw new JwtParseException();
        }
        JWTClaimsSet jwtClaimsSet;
        try {
            jwtClaimsSet = jwt.getJWTClaimsSet();
        } catch (ParseException e) {
            log.error("error during parse jwt claim", e);
            throw new JwtParseClaimException();
        }
        log.trace("getting clain for {}", field);
        if (jwtClaimsSet.getClaim(field) instanceof String) {
            String claim = (String) jwtClaimsSet.getClaim(field);
            Set<String> objects = new HashSet<>();
            objects.add(claim);
            return objects;
        }
        if (jwtClaimsSet.getClaim(field) instanceof Long)
            return new HashSet<>(List.of(((Long) jwtClaimsSet.getClaim(field)).toString()));
        if (jwtClaimsSet.getClaim(field) == null)
            return Collections.emptySet();
        if (jwtClaimsSet.getClaim(field) instanceof Date) {
            long epochSecond = ((Date) jwtClaimsSet.getClaim(field)).toInstant().getEpochSecond();
            return new HashSet<>(List.of(String.valueOf(epochSecond)));
        }
        List<String> resourceIds = (List<String>) jwtClaimsSet.getClaim(field);
        return new HashSet<>(resourceIds);
    }

    public static class JwtParseException extends RuntimeException {
    }

    public static class JwtParseClaimException extends RuntimeException {
    }
}
