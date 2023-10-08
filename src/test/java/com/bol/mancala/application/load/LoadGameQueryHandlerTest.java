package com.bol.mancala.application.load;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.infrastructure.InMemoryGames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class LoadGameQueryHandlerTest {
    InMemoryGames repository = new InMemoryGames();
    LoadGameQueryHandler handler = new LoadGameQueryHandler(repository);
    Game game = new Game("game-xxx", Board.create("Arthur", "Ford"), new Date());

    @Test
    void givenTheGameIdThenReturnItsBoard() {
        repository.store(game);
        LoadGameQuery query = new LoadGameQuery("game-xxx");

        LoadGameQuery.Response board = handler.handle(query);

        Assertions.assertEquals(
                new LoadGameQuery.Response(
                        "game-xxx",
                        new LoadGameQuery.Response.BoardSide("Arthur", List.of(6, 6, 6, 6, 6, 6), 0),
                        new LoadGameQuery.Response.BoardSide("Ford", List.of(6, 6, 6, 6, 6, 6), 0),
                        Board.Player.FIRST,
                        false,
                        null
                ),
                board
        );
    }
}
