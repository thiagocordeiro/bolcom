package com.bol.mancala.domain.exception;

public abstract class DomainException extends RuntimeException {
    DomainException(String message) {
        super(message);
    }
}
