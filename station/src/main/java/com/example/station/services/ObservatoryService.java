package com.example.station.services;

import com.example.station.models.Delay;
import com.example.station.models.Planets;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Service
public class ObservatoryService {
//    @Value("${planets.service.url}")
//    private String planetsServiceBaseUrl;

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081")
            .build();

    public Mono<String> observePlanets(String delayTime) {
        Delay delay = Delay.builder()
                .delayTime(delayTime)
                .build();

        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.pathSegment("planets").build())
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .header(ACCEPT, APPLICATION_JSON_UTF8_VALUE)
                .body(fromObject(delay))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<ResponseEntity<Delay>> observePlanetsExchange(String delayTime) {
        Delay delay = Delay.builder()
                .delayTime(delayTime)
                .build();

        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.pathSegment("planets").build())
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .header(ACCEPT, APPLICATION_JSON_UTF8_VALUE)
                .body(Mono.just(delay), Delay.class)
                .exchange()
                .flatMap(response -> response.toEntity(Delay.class))
                .doOnSuccess(o -> System.out.println("POST OUTPUT: " + o));
    }

    public void testBackPressure(String delayTime) {
        Delay delay = Delay.builder()
                .delayTime(delayTime)
                .build();
        List<Planets> elements = new ArrayList<>();
        Instant start = Instant.now();

        webClient
                .post()
                .uri(uriBuilder -> uriBuilder.pathSegment("planets").build())
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .header(ACCEPT, APPLICATION_JSON_UTF8_VALUE)
                .body(fromObject(delay))
                .retrieve()
                .bodyToFlux(Planets.class)
//                .log()
                .subscribe(new Subscriber<Planets>() {
                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(2);
                    }

                    @Override
                    public void onNext(Planets planets) {
                        System.out.println("planets: " + planets.getPlanet());
                        elements.add(planets);
                        onNextAmount++;
                        if (onNextAmount % 2 == 0) {
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {}

                    @Override
                    public void onComplete() {
                        Instant end = Instant.now();
                        System.out.println("Total Elapsed time: " + Duration.between(start, end).toMillis());
                    }
                });
//                .limitRate(100)
//                .doOnComplete(() ->  {
//                    long end = System.currentTimeMillis();
//                    System.out.println("Total Elapsed time: " + (end - start)/1000 + " seconds");
//                });
//                .exchange()
//                .flatMap(response -> response.bodyToFlux<Integer>);

//        return result;
    }

    public void testBackPressureClassic() {
        List<Integer> elements = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(2);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elements.add(integer);
                        onNextAmount++;
                        if (onNextAmount % 2 == 0) {
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {}

                    @Override
                    public void onComplete() {}
                });

        System.out.println(Arrays.toString(elements.toArray()));
    }

}
