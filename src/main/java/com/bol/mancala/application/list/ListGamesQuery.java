package com.bol.mancala.application.list;

import io.micronaut.serde.annotation.Serdeable;

import java.util.Date;

public record ListGamesQuery() {
    @Serdeable.Serializable
    @Serdeable.Deserializable
    public record Response(
            String id,
            String first,
            String second,
            Date createdAt
    ) {
    }
}
