package com.hw.clazz;

public class Constant {
    public static String[] ignoreApi = {
            "/oauth/token/**",
            "/oauth/token_key/**",
            "/api/v1/**"
    };
    public static String EDGE_PROXY_TOKEN_REVOKED = "ep_token_revoked";
    public static String EDGE_PROXY_UNAUTHORIZED_ACCESS = "ep_unauthorized_access";
}
