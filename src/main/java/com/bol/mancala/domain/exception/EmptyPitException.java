package com.bol.mancala.domain.exception;

public class EmptyPitException extends DomainException.BusinessRuleException {
    public EmptyPitException(int pitIndex) {
        super("Pit " + pitIndex + " is empty");
    }
}
