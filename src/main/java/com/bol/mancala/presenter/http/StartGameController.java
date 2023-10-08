package com.bol.mancala.presenter.http;

import com.bol.mancala.application.start.StartGameCommand;
import com.bol.mancala.application.start.StartGameCommandHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/games/mancala")
public class StartGameController {
    private final StartGameCommandHandler handler;

    @Inject
    StartGameController(StartGameCommandHandler handler) {
        this.handler = handler;
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> index(@Body StartGameCommand command) {
        StartGameCommand.Response response = handler.handle(command);

        return HttpResponse
                .status(HttpStatus.OK)
                .body(response);
    }
}
