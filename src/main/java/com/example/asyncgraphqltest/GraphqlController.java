package com.example.asyncgraphqltest;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
public class GraphqlController {
    long interval = 100;
    long multiple = 8;

    @SubscriptionMapping
    public Flux<Video> searchVideo() {
        return Flux.interval(Duration.ofMillis(interval)).map(s -> new Video(s.intValue() + 1))
                .take(9);
    }

    @SchemaMapping
    public String name(Video video) {
        return String.valueOf((char) (video.id + 64));
    }

    @SchemaMapping
    public Mono<Boolean> isFavorite(Video video) {
        long delay = (interval * multiple) - (interval * multiple / 10) * video.id;
        return Mono.delay(Duration.ofMillis(delay))
                .then(Mono.just(Boolean.TRUE))
                .doOnNext(s -> System.out.println("SchemaMapping: " + video.id + " delay: " + delay));
    }
}
