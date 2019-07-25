package com.hw.clazz;

public class Constant {
    public static String[] ignoreApi = {
            "/oauth/token/**",
            "/oauth/token_key/**",
            "/api/v*/**"
    };
    public static String EDGE_PROXY_TOKEN_REVOKED = "ep_token_revoked";
    public static String EDGE_PROXY_UNAUTHORIZED_ACCESS = "ep_unauthorized_access";
    public static String EDGE_PROXY_UNMAPPED_ROUTE = "ep_unmapped_route";
}
