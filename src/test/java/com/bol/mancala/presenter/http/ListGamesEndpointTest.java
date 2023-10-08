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

public class ListGamesEndpointTest extends MicronautHttpTestCase {
    @Test
    void loadAllStoredGames() {
        repository.store(
                new Game("game-arthur-ford", Board.create("Arthur Dent", "Ford Prefect"), new Date(1696776960000L))
        );
        repository.store(
                new Game("game-travolta-bacon", Board.create("John Travolta", "Kevin Bacon"), new Date(1696777033000L))
        );

        HttpResponse<?> response = runHttpCall();

        assertEquals(HttpStatus.OK, response.status());
        assertJsonResponseBody(
                """
                [
                  {
                    "id": "game-arthur-ford",
                    "first": "Arthur Dent",
                    "second": "Ford Prefect",
                    "createdAt": "2023-10-08T14:56:00Z"
                  },
                  {
                    "id": "game-travolta-bacon",
                    "first": "John Travolta",
                    "second": "Kevin Bacon",
                    "createdAt": "2023-10-08T14:57:13Z"
                  }
                ]
                """,
                response
        );
    }

    @Override
    protected HttpResponse<?> runHttpCall() {
        HttpRequest<String> request = HttpRequest.GET("/api/games/mancala");

        try {
            return client.toBlocking().exchange(request);
        } catch (HttpClientResponseException exception) {
            return exception.getResponse();
        }
    }
}
