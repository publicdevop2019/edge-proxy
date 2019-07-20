package com.hw.auth.clazz.eenum;

import com.hw.auth.converter.EnumConverter;

public enum ScopeEnum {
    write,
    read,
    trust;

    public static class ScopeConverter extends EnumConverter {
        public ScopeConverter() {
            super(ScopeEnum.class);
        }
    }
}
