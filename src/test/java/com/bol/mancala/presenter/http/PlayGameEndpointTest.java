package com.bol.mancala.presenter.http;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.presenter.http.micronaut.MicronautHttpTestCase;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayGameEndpointTest extends MicronautHttpTestCase {
    String payload = """
            {
                 "player": "FIRST",
                 "pit": 2
             }
            """;

    Game game = new Game("game-xxx", Board.create("Arthur", "Ford"), new Date());

    @Test
    void testGivenGameIdWhenGameDoesNotExistThenReturnNotFound() {
        repository.store(new Game("game-vvv", Board.create("Arthur", "Ford"), new Date()));

        HttpResponse<?> response = runHttpCall();

        assertEquals(HttpStatus.NOT_FOUND, response.status());
        assertJsonResponseBody(
                """
                        {
                          "message": "Game Not Found"
                        }
                        """,
                response
        );
    }

    @Test
    void testGivenGameIdWhenThePlayerIsWrongThenReturnUnprocessableEntity() {
        Game game = new Game("game-xxx", Board.create("Arthur", "Ford"), new Date());
        game.board().play(4, Board.Player.FIRST);
        repository.store(game);

        HttpResponse<?> response = runHttpCall();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status());
        assertJsonResponseBody(
                """
                        {
                          "message": "Not the turn of FIRST player"
                        }
                        """,
                response
        );
    }

    @Test
    void testGivenGameIdWhenPlayedPitIsEmptyThenReturnUnprocessableEntity() {
        Game game = new Game("game-xxx", Board.create("Arthur", "Ford"), new Date());
        game.board().top.pits().set(2, 0);
        repository.store(game);

        HttpResponse<?> response = runHttpCall();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status());
        assertJsonResponseBody(
                """
                        {
                          "message": "Pit 2 is empty"
                        }
                        """,
                response
        );
    }

    @Test
    void testGivenGameIdWhenPlayedPitDoesNotExistThenReturnUnprocessableEntity() {
        Game game = new Game("game-xxx", Board.create("Arthur", "Ford"), new Date());
        repository.store(game);
        payload = """
                {
                     "player": "FIRST",
                     "pit": 15
                 }
                """;

        HttpResponse<?> response = runHttpCall();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status());
        assertJsonResponseBody(
                """
                        {
                          "message": "Pit 15 does not exist"
                        }
                        """,
                response
        );
    }

    @Test
    void testThePlayInfoThenRunTheTurn() {
        repository.store(game);

        HttpResponse<?> response = runHttpCall();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.status());
        Assertions.assertEquals(0, game.board().top.amountOfStonesInPit(2));
        Assertions.assertEquals(1, game.board().top.amountOfStonesInLargerPit());
        Assertions.assertEquals(Board.Player.SECOND, game.board().turn());
    }

    @Override
    protected HttpResponse<?> runHttpCall() {
        HttpRequest<String> request = HttpRequest.POST("/games/mancala/game-xxx", payload);

        try {
            return client.toBlocking().exchange(request);
        } catch (HttpClientResponseException exception) {
            return exception.getResponse();
        }
    }
}
