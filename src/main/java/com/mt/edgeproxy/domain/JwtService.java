package com.mt.edgeproxy.domain;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Slf4j
@Service
public class JwtService {

    public List<String> getResourceIds(String jwtRaw) {
        return getClaims(jwtRaw, "aud");
    }

    public List<String> getScopes(String jwtRaw) {
        return getClaims(jwtRaw, "scope");
    }

    public List<String> getRoles(String jwtRaw) {
        return getClaims(jwtRaw, "authorities");
    }

    public boolean isUser(String jwtRaw) {
        List<String> uid = getClaims(jwtRaw, "uid");
        return uid != null && !uid.isEmpty() && uid.get(0) != null;
    }

    private List<String> getClaims(String jwtRaw, String field) {
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
        if (jwtClaimsSet.getClaim(field) instanceof String)
            return List.of((String) jwtClaimsSet.getClaim(field));
        List<String> resourceIds = (List<String>) jwtClaimsSet.getClaim(field);
        return resourceIds;
    }

    public static class JwtParseException extends RuntimeException {
    }

    public static class JwtParseClaimException extends RuntimeException {
    }
}
