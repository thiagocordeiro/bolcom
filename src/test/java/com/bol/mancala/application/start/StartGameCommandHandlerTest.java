package com.bol.mancala.application.start;

import com.bol.mancala.application.start.StartGameCommand.Response;
import com.bol.mancala.domain.Game;
import com.bol.mancala.infrastructure.InMemoryGames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StartGameCommandHandlerTest {
    InMemoryGames repository = new InMemoryGames();
    StartGameCommandHandler handler = new StartGameCommandHandler(repository);

    @Test
    void givenTheCommandThenCreateNewGame() {
        StartGameCommand command = new StartGameCommand("Arthur Dent", "Ford Prefect");

        Response response = handler.handle(command);

        Game game = repository.loadById(response.id());
        Assertions.assertInstanceOf(Game.class, game);
    }
}
