package com.techsensei.payment_intergration_system.backend.common.exception;

public class ForbiddenException {
    public class ForbiddenException
            extends RuntimeException {

        public ForbiddenException(String message) {
            super(message);
        }
    }
}
