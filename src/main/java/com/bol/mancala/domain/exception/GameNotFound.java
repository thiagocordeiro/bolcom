package com.bol.mancala.domain.exception;

public class GameNotFound extends DomainException.NotFoundException {
    public GameNotFound() {
        super("Game Not Found");
    }
}
