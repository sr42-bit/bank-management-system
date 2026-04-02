package com.corebanking.engine.infrastructure.web;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.corebanking.engine.domain.model.exception.DomainValidationException;
import com.corebanking.engine.shared.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ✅ DTO VALIDATION
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));

        log.warn("Validation failed: {}", fieldErrors);

        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(false, fieldErrors, "Validation Failed"));
    }

    // ✅ DOMAIN / BAD REQUEST
    @ExceptionHandler({IllegalArgumentException.class, DomainValidationException.class})
    public ResponseEntity<ApiResponse<String>> handleBadRequest(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.warn("Bad request at {} : {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<String>> handleGeneric(
        Exception ex,
        HttpServletRequest request
) {
    log.error("Internal error at {} ", request.getRequestURI(), ex);

    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(ex.getMessage())); 
}
}