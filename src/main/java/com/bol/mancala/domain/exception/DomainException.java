package com.bol.mancala.domain.exception;

public abstract class DomainException extends RuntimeException {
    public abstract static class BusinessRuleException extends DomainException {
        public BusinessRuleException(String message) {
            super(message);
        }
    }

    public abstract static class NotFoundException extends DomainException {
        public NotFoundException(String message) {
            super(message);
        }
    }

    DomainException(String message) {
        super(message);
    }
}
