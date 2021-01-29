package com.mt.edgeproxy.infrastructure;

import com.mt.edgeproxy.domain.JwtService;
import com.mt.edgeproxy.infrastructure.springcloudgateway.exception.GzipException;
import com.mt.edgeproxy.infrastructure.springcloudgateway.exception.ResourceCloseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.UUID;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {
    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<?> handle403Exception(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(ex));
    }

    @ExceptionHandler(value = {
            JwtService.JwtParseException.class,
            JwtService.JwtParseClaimException.class
    })
    protected ResponseEntity<?> handle400Exception(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex));
    }

    @ExceptionHandler(value = {
            ResourceCloseException.class,
            GzipException.class
    })
    protected ResponseEntity<?> handle500Exception(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(ex));
    }

    @Slf4j
    @Data
    @AllArgsConstructor
    public static class ErrorMessage {
        private List<String> errors;
        private String errorId;

        public ErrorMessage(RuntimeException ex) {
            errorId = UUID.randomUUID().toString();
            List<String> strings;
            if (NestedExceptionUtils.getMostSpecificCause(ex).getMessage() != null) {
                strings = List.of(NestedExceptionUtils.getMostSpecificCause(ex).getMessage().replace("\t", "").split("\n"));
                log.error("Handled exception UUID - {} - class - [{}] - Exception :", errorId, ex.getClass(), ex);
            } else {
                strings = List.of("Unable to get most specific cause, see log");
                log.error("Unhandled exception UUID - {} - class - [{}] - Exception :", errorId, ex.getClass(), ex);
            }
            errors = strings;
        }
    }
}
