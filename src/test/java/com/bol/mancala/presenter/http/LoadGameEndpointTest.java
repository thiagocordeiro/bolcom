package com.bol.mancala.presenter.http;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.presenter.http.micronaut.MicronautHttpTestCase;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadGameEndpointTest extends MicronautHttpTestCase {
    @Test
    void testGivenGameIdThenReturnItsCurrentBoardState() {
        repository.store(new Game("game-xxx", Board.create("Arthur", "Ford"), new Date()));

        HttpResponse<?> response = runHttpCall();

        assertEquals(HttpStatus.OK, response.status());
        assertJsonResponseBody(
                """
                {
                  "id": "game-xxx",
                  "top": {
                    "player": "Arthur",
                    "pits": [ 6, 6, 6, 6, 6, 6 ],
                    "big": 0
                  },
                  "bottom": {
                    "player": "Ford",
                    "pits": [ 6, 6, 6, 6, 6, 6 ],
                    "big": 0
                  },
                  "turn": "FIRST",
                  "finished": false
                }
                """,
                response
        );
    }

    @Test
    void testGivenAClosedGameIdThenReturnItsCurrentBoardState() {
        Game game = new Game("game-xxx", Board.create("Arthur", "Ford"), new Date());
        game.board().play(5, Board.Player.FIRST);
        game.board().top.finish();
        game.board().bottom.finish();
        repository.store(game);

        HttpResponse<?> response = runHttpCall();

        assertEquals(HttpStatus.OK, response.status());
        assertJsonResponseBody(
                """
                {
                  "id": "game-xxx",
                  "top": {
                    "player": "Arthur",
                    "pits": [ 0, 0, 0, 0, 0, 0 ],
                    "big": 31
                  },
                  "bottom": {
                    "player": "Ford",
                    "pits": [ 0, 0, 0, 0, 0, 0 ],
                    "big": 41
                  },
                  "turn": "SECOND",
                  "finished": true,
                  "winner": "SECOND"
                }
                """,
                response
        );
    }

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

    @Override
    protected HttpResponse<?> runHttpCall() {
        HttpRequest<String> request = HttpRequest.GET("/api/games/mancala/game-xxx");

        try {
            return client.toBlocking().exchange(request);
        } catch (HttpClientResponseException exception) {
            return exception.getResponse();
        }
    }
}
