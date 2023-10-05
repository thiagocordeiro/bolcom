package com.bol.mancala.domain.exception;

import com.bol.mancala.domain.Board;

public class WrongPlayerException extends DomainException {
    public WrongPlayerException(Board.Player turn) {
        super("Not the turn of " + turn.name());
    }
}
