package com.bol.mancala.domain;

import java.util.Date;
import java.util.UUID;

public record Game(String id, Board board, Date createdAt) {
    public static Game create(String playerOne, String playerTwo) {
        return new Game(
                UUID.randomUUID().toString(),
                Board.create(playerOne, playerTwo),
                new Date()
        );
    }

    public boolean isFinished() {
        return this.board.isFinished();
    }
}
