package com.bol.mancala.application.play;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.infrastructure.InMemoryGames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class PlayGameCommandHandlerTest {
    InMemoryGames repository = new InMemoryGames();
    PlayGameCommandHandler handler = new PlayGameCommandHandler(repository);
    Game game = new Game("game-xxx", Board.create("Arthur", "Ford"), new Date());

    @Test
    void givenPlayInfoThenRunPlayAccordingly() {
        repository.store(game);
        PlayGameCommand command = new PlayGameCommand("game-xxx", Board.Player.FIRST, 3);

        handler.handle(command);

        Assertions.assertEquals(0, game.board().top.amountOfStonesInPit(3));
        Assertions.assertEquals(1, game.board().top.amountOfStonesInLargerPit());
        Assertions.assertEquals(Board.Player.SECOND, game.board().turn());
    }
}
