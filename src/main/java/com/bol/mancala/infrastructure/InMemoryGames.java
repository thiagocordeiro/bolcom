package com.bol.mancala.infrastructure;

import com.bol.mancala.domain.Game;
import com.bol.mancala.domain.Games;
import com.bol.mancala.domain.exception.GameNotFound;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class InMemoryGames implements Games {
    Map<String, Game> games = new HashMap<>();

    @Override
    public void store(Game game) {
        games.put(game.id(), game);
    }

    @Override
    public Game loadById(String id) {
        Game game = games.get(id);

        if (game == null) {
            throw new GameNotFound();
        }

        return game;
    }

    @Override
    public List<Game> ongoing() {
        return games.values()
                .stream()
                .filter(game -> !game.isFinished())
                .toList();
    }

    public Game first() {
        return games.values().stream().toList().get(0);
    }

    public void reset() {
        games = new HashMap<>();
    }
}
