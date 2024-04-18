package com.example.asyncgraphqltest;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
public class GraphqlController {
    @SubscriptionMapping
    public Flux<Video> searchVideo() {
        return Flux.interval(Duration.ofMillis(200)).map(s -> new Video(s.intValue() + 1, "200" + (s + 1))).take(9).doOnNext(video -> System.out.println(video.vodTime));
    }

    @SchemaMapping
    public String name(Video video) {
        return String.valueOf((char) (video.id + 64));
    }

    @SchemaMapping
    public Mono<Boolean> isFavorite(Video video) {
        //correct
//        return Mono.delay(Duration.ofMillis(1000 - 100 * (long) video.id)).then(Mono.just(Boolean.TRUE)).doOnNext(s -> System.out.println("SchemaMapping: " + video.id));
        //wrong
        return Mono.delay(Duration.ofMillis(2000 - 200 * (long) video.id)).then(Mono.just(Boolean.TRUE)).doOnNext(s -> System.out.println("SchemaMapping: " + video.id));
    }
}
