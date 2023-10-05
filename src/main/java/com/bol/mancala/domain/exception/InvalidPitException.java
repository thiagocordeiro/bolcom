package com.bol.mancala.domain.exception;

public class InvalidPitException extends DomainException {
    public InvalidPitException(int pitIndex) {
        super("Pit " + (pitIndex + 1) + " does not exist");
    }
}
