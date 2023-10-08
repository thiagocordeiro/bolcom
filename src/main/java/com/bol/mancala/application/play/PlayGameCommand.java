package com.bol.mancala.application.play;

import com.bol.mancala.domain.Board;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;

public record PlayGameCommand(
        String gameId,
        Board.Player player,
        int pit
) {
    @Serdeable.Deserializable
    public record Request(
            @NonNull Board.Player player,
            @NonNull int pit
    ) {
    }
}
