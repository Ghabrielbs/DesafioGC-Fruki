package com.example.demo.clima.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Localizacao(
        String name,
        Double latitude,
        Double longitude,
        String timezone
) 
{}