package com.bol.mancala.application.list;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.infrastructure.InMemoryGames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class ListGameQueryHandlerTest {
    InMemoryGames repository = new InMemoryGames();
    ListGamesQueryHandler handler = new ListGamesQueryHandler(repository);

    @Test
    void returnListOfGames() {
        Date createAt = new Date();
        repository.store(new Game("game-arthur-ford", Board.create("Arthur Dent", "Ford Prefect"), createAt));
        repository.store(new Game("game-travolta-bacon", Board.create("John Travolta", "Kevin Bacon"), createAt));

        List<ListGamesQuery.Response> response = handler.handle();

        Assertions.assertEquals(
                List.of(
                        new ListGamesQuery.Response("game-arthur-ford", "Arthur Dent", "Ford Prefect", createAt),
                        new ListGamesQuery.Response("game-travolta-bacon", "John Travolta", "Kevin Bacon", createAt)
                ),
                response
        );
    }
}
