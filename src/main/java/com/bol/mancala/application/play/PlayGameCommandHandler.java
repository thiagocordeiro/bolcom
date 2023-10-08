package com.bol.mancala.application.play;

import com.bol.mancala.domain.Game;
import com.bol.mancala.domain.Games;
import jakarta.inject.Singleton;

@Singleton
public class PlayGameCommandHandler {
    private final Games games;

    PlayGameCommandHandler(Games games) {
        this.games = games;
    }

    public void handle(PlayGameCommand command) {
        Game game = games.loadById(command.gameId());

        game.board()
                .play(
                        command.pit(),
                        command.player()
                );
    }
}
