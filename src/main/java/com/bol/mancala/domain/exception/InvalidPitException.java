package com.bol.mancala.domain.exception;

public class InvalidPitException extends DomainException.BusinessRuleException {
    public InvalidPitException(int pitIndex) {
        super("Pit " + pitIndex + " does not exist");
    }
}
