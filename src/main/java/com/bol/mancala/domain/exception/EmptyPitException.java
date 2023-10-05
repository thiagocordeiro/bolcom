package com.bol.mancala.domain.exception;

public class EmptyPitException extends DomainException {
    public EmptyPitException(int pitIndex) {
        super("Pit " + (pitIndex + 1) + " is empty");
    }
}
