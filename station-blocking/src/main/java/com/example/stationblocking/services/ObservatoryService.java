package com.example.stationblocking.services;

import com.example.stationblocking.models.Delay;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ObservatoryService {
    private final RestTemplate client;
    private static final String UNIVERSE_SERVICE_URL = "http://localhost:8081";

    public ObservatoryService(RestTemplateBuilder builder) {
        client = builder.rootUri(UNIVERSE_SERVICE_URL).build();
    }
    public String observePlanets(String delayTime) {
        Delay delay = Delay.builder()
                .delayTime(delayTime)
                .build();

        return client.postForObject("/planets", delay, String.class);
    }
}
