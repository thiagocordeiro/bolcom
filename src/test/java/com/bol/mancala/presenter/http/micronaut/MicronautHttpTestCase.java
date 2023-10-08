package com.bol.mancala.presenter.http.micronaut;

import com.bol.mancala.infrastructure.InMemoryGames;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public abstract class MicronautHttpTestCase {
    @Inject
    protected EmbeddedServer server;

    @Inject
    @Client("/")
    protected HttpClient client;

    @Inject
    protected InMemoryGames repository;

    @Inject
    ObjectMapper mapper;

    protected abstract HttpResponse<?> runHttpCall();

    @BeforeEach
    void setup() {
        repository.reset();
    }

    protected void assertJsonResponseBody(String expectedBody, HttpResponse<?> response) {
        try {
            String body = response.getBody(String.class).get();
            Object expected = mapper.readValue(expectedBody, Object.class);
            Object actual = response.getBody(Object.class).get();

            assertEquals(expected, actual, "Expected body <" + expectedBody + "> does not match <" + body + ">");
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
