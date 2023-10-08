package com.bol.mancala.presenter.http.errors;

import com.bol.mancala.domain.exception.DomainException.NotFoundException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.util.Map;

@Produces
@Singleton
@Requires(classes = {NotFoundException.class, ExceptionHandler.class})
public class NotFoundExceptionHandler implements ExceptionHandler<NotFoundException, HttpResponse<?>> {
    @Override
    public HttpResponse<Map<String, String>> handle(HttpRequest request, NotFoundException exception) {
        return HttpResponse
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }
}
