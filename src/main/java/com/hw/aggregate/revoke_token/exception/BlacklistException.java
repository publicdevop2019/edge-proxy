package com.hw.aggregate.revoke_token.exception;

public class BlacklistException extends RuntimeException {
    public BlacklistException(String message) {
        super(message);
    }
}
