package com.hw.auth.clazz.eenum;

import com.hw.auth.converter.DefaultAuthorityConverter;

public enum ClientAuthorityEnum {
    ROLE_FRONTEND,
    ROLE_BACKEND,
    ROLE_FIRST_PARTY,
    ROLE_THIRD_PARTY,
    ROLE_TRUST,
    /**
     * root client can not be deleted
     */
    ROLE_ROOT;

    public static class ClientAuthorityConverter extends DefaultAuthorityConverter {
        public ClientAuthorityConverter() {
            super(ClientAuthorityEnum.class);
        }
    }
}
