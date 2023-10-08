package com.bol.mancala.application.start;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable.Deserializable
public record StartGameCommand(
        @NonNull String firstPlayer,
        @NonNull String secondPlayer
) {
    @Serdeable.Serializable
    public record Response(String id) {
    }
}
