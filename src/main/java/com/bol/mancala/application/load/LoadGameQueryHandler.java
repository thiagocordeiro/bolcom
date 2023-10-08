package com.bol.mancala.application.load;

import com.bol.mancala.domain.Game;
import com.bol.mancala.domain.Games;
import jakarta.inject.Singleton;

@Singleton
public class LoadGameQueryHandler {
    private final Games games;

    LoadGameQueryHandler(Games games) {
        this.games = games;
    }

    public LoadGameQuery.Response handle(LoadGameQuery query) {
        Game game = games.loadById(query.id());

        return new LoadGameQuery.Response(
                game.id(),
                new LoadGameQuery.Response.BoardSide(
                        game.board().top.player(),
                        game.board().top.pits(),
                        game.board().top.big()
                ),
                new LoadGameQuery.Response.BoardSide(
                        game.board().bottom.player(),
                        game.board().bottom.pits(),
                        game.board().bottom.big()
                ),
                game.board().turn(),
                game.board().isFinished(),
                game.board().winner() != null ?
                        new LoadGameQuery.Response.BoardSide(
                                game.board().winner().player(),
                                game.board().winner().pits(),
                                game.board().winner().big()
                        ) : null
        );
    }

}
