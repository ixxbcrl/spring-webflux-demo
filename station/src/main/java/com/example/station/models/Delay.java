package com.example.station.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Delay {
    private String delayTime;
}
