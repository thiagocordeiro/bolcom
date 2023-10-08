package com.bol.mancala.application.list;

import com.bol.mancala.domain.Games;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class ListGamesQueryHandler {
    private final Games games;

    ListGamesQueryHandler(Games games) {
        this.games = games;
    }

    public List<ListGamesQuery.Response> handle() {
        return games.ongoing()
                .stream()
                .map(game -> new ListGamesQuery.Response(
                        game.id(),
                        game.board().top.player(),
                        game.board().bottom.player(),
                        game.createdAt()
                ))
                .toList();
    }

}
