package com.bol.mancala.presenter.http;

import com.bol.mancala.application.play.PlayGameCommand;
import com.bol.mancala.application.play.PlayGameCommandHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/games/mancala/{gameId}")
public class PlayGameController {
    private final PlayGameCommandHandler handler;

    @Inject
    PlayGameController(PlayGameCommandHandler handler) {
        this.handler = handler;
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> index(@PathVariable String gameId, @Body PlayGameCommand.Request request) {
        PlayGameCommand command = new PlayGameCommand(gameId, request.player(), request.pit());

        handler.handle(command);

        return HttpResponse
                .status(HttpStatus.NO_CONTENT);
    }
}
