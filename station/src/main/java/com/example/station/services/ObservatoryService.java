package com.example.station.services;

import com.example.station.models.Delay;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

}
