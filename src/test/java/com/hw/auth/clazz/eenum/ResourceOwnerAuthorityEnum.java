package com.hw.auth.clazz.eenum;

import com.hw.auth.converter.DefaultAuthorityConverter;

public enum ResourceOwnerAuthorityEnum {
    ROLE_ADMIN,
    ROLE_ROOT,
    ROLE_USER;

    public static class ResourceOwnerAuthorityConverter extends DefaultAuthorityConverter {
        public ResourceOwnerAuthorityConverter() {
            super(ResourceOwnerAuthorityEnum.class);
        }
    }

}
