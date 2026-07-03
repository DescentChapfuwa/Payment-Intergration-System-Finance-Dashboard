package com.techsensei.payment_intergration_system.backend.common.handler;

import com.techsensei.payment_intergration_system.backend.common.dto.ErrorResponse;
import com.techsensei.payment_intergration_system.backend.common.exception.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(
                        ResourceNotFoundException ex, HttpServletRequest request) {

                log.error("Resource not found", ex);

                ErrorResponse error = ErrorResponse.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(ex.getMessage())
                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(
                        BadRequestException ex, HttpServletRequest request) {

                log.error("Bad Request Exception", ex);

                ErrorResponse error = ErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .path(request.getRequestURI())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.badRequest()
                                .body(error);
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ErrorResponse> handleUnauthorized(
                        UnauthorizedException ex, HttpServletRequest request) {

                log.error("Unauthorized Exception", ex);

                ErrorResponse error = ErrorResponse.builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .path(request.getRequestURI())
                                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(
                        MethodArgumentNotValidException ex, HttpServletRequest request) {

                log.error("Method Argument Not Valid Exception", ex);

                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.put(
                                                error.getField(),
                                                error.getDefaultMessage()));

                ErrorResponse response = ErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message("Validation failed")
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .errors(errors)
                                .build();

                return ResponseEntity.badRequest()
                                .body(response);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleForbidden(
                        ForbiddenException ex, HttpServletRequest request) {

                log.error("Access Denied Exception", ex);

                ErrorResponse error = ErrorResponse.builder()
                                .status(HttpStatus.FORBIDDEN.value())
                                .path(request.getRequestURI())
                                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                                .message("Access denied")
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(error);
        }

        @ExceptionHandler(InsufficientBalanceException.class)
        public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex,
                        HttpServletRequest request) {

                log.error("Insufficient Balance Exception", ex);

                ErrorResponse error = ErrorResponse.builder()
                                .status(400)
                                .path(request.getRequestURI())
                                .error("Insufficient Balance Exception")
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.badRequest().body(error);
        }

        @ExceptionHandler(InvalidPaymentException.class)
        public ResponseEntity<ErrorResponse> handleInvalidPayment(InvalidPaymentException ex,
                        HttpServletRequest request) {

                log.error("Invalid Payment", ex);

                ErrorResponse error = ErrorResponse.builder()
                                .status(400)
                                .path(request.getRequestURI())
                                .error("Invalid Payment")
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.badRequest().body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleException(
                        Exception ex,
                        HttpServletRequest request) {

                log.error("Unexpected error", ex);

                ErrorResponse response = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .message("An unexpected error occurred.")
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(response);
        }

}
