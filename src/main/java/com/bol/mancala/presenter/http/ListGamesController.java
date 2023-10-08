package com.bol.mancala.presenter.http;

import com.bol.mancala.application.list.ListGamesQuery;
import com.bol.mancala.application.list.ListGamesQueryHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/api/games/mancala")
public class ListGamesController {
    private final ListGamesQueryHandler handler;

    @Inject
    ListGamesController(ListGamesQueryHandler handler) {
        this.handler = handler;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> index() {
        List<ListGamesQuery.Response> response = handler.handle();

        return HttpResponse
                .status(HttpStatus.OK)
                .body(response);
    }
}
