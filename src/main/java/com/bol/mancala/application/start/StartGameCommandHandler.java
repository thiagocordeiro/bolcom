package com.bol.mancala.application.start;

import com.bol.mancala.domain.Game;
import com.bol.mancala.domain.Games;
import jakarta.inject.Singleton;

@Singleton
public class StartGameCommandHandler {
    private final Games games;

    StartGameCommandHandler(Games games) {
        this.games = games;
    }

    public StartGameCommand.Response handle(StartGameCommand command) {
        Game game = Game.create(command.firstPlayer(), command.secondPlayer());

        games.store(game);

        return new StartGameCommand.Response(game.id());
    }
}
