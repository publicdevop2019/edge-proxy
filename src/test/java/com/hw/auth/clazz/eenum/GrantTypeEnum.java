package com.hw.auth.clazz.eenum;

import com.hw.auth.converter.EnumConverter;

public enum GrantTypeEnum {
    client_credentials,
    password,
    refresh_token,
    authorization_code;

    public static class GrantTypeConverter extends EnumConverter {
        public GrantTypeConverter() {
            super(GrantTypeEnum.class);
        }
    }
}
