package com.techsensei.payment_intergration_system.backend.common.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
            super(message);
        }
}
