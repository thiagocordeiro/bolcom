package com.bol.mancala.presenter.http;

import com.bol.mancala.application.load.LoadGameQuery;
import com.bol.mancala.application.load.LoadGameQueryHandler;
import com.bol.mancala.domain.Board;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;

@Controller("/games/mancala/{gameId}")
public class LoadGameController {
    private final LoadGameQueryHandler handler;

    @Inject
    LoadGameController(LoadGameQueryHandler handler) {
        this.handler = handler;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> index(@PathVariable String gameId) {
        LoadGameQuery query = new LoadGameQuery(gameId);

        LoadGameQuery.Response response = handler.handle(query);

        return HttpResponse
                .status(HttpStatus.OK)
                .body(response);
    }
}
