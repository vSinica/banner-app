package ru.vados.BannerApp.Controller;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.vados.BannerApp.Exception.HaveBannerInCategoryWhenDelete;
import ru.vados.BannerApp.Exception.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof NotFoundException){
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return writeErrorResponse(exchange, ex.getMessage());
        }

        if (ex instanceof HaveBannerInCategoryWhenDelete){
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return writeErrorResponse(exchange, ex.getMessage());
        }

        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return writeErrorResponse(exchange, "INTERNAL_SERVER_ERROR");
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange,String errorMessage) {
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap(errorMessage.getBytes())));
    }

}
