package com.bol.mancala.presenter.http;

import com.bol.mancala.domain.Game;
import com.bol.mancala.presenter.http.micronaut.MicronautHttpTestCase;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class StartGameEndpointTest extends MicronautHttpTestCase {
    @Test
    void testGivenThePlayersThenStartNewGame() {
        HttpResponse<?> response = runHttpCall();

        Game game = repository.first();
        Assertions.assertEquals(HttpStatus.OK, response.status());
        assertJsonResponseBody("{\"id\": \""+ game.id() + "\"}", response);
    }

    @Override
    protected HttpResponse<?> runHttpCall() {
        HttpRequest<String> request = HttpRequest.POST(
                "/api/games/mancala",
                """
                    {
                         "firstPlayer": "Arthur Dent",
                         "secondPlayer": "Ford Prefect"
                     }
                """
        );

        return client.toBlocking().exchange(request);
    }
}
