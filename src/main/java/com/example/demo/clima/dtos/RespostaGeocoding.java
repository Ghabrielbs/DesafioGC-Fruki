package com.example.demo.clima.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespostaGeocoding(
    List<Localizacao> results
) 
{}