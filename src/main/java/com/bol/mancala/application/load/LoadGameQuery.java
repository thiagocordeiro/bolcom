package com.bol.mancala.application.load;

import com.bol.mancala.domain.Board;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

public record LoadGameQuery(String id) {
    @Serdeable.Serializable
    @Serdeable.Deserializable
    public record Response(
            String id,
            BoardSide top,
            BoardSide bottom,
            Board.Player turn,
            boolean finished,
            BoardSide winner
    ) {
        @Serdeable.Serializable
        @Serdeable.Deserializable
        public record BoardSide(String player, List<Integer> pits, int big) {
        }
    }
}
