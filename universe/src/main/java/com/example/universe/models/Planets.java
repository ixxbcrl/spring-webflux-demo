package com.example.universe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@ToString
public class Planets {
    @Id
    private String id;
    private String planet;
    private Info info;
}
