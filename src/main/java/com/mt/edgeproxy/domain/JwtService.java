package com.mt.edgeproxy.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    @Autowired
    ObjectMapper mapper;

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
        Jwt jwt = JwtHelper.decode(jwtRaw);
        Map<String, Object> claims;
        try {
            claims = mapper.readValue(jwt.getClaims(), Map.class);
        } catch (IOException e) {
            //this block is purposely left blank
            claims = new HashMap<>();
        }
        List<String> resourceIds = (ArrayList<String>) claims.get(field);
        return resourceIds;
    }
}
