package com.mt.edgeproxy.domain;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<String> resourceIds = (List<String>) jwtClaimsSet.getClaim(field);
        Set<String> strings = new HashSet<>(resourceIds);
        return strings;
    }

    public static class JwtParseException extends RuntimeException {
    }

    public static class JwtParseClaimException extends RuntimeException {
    }
}
