
package com.example.demo.clima.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Localizacao(
        String name,
        Double latitude,
        Double longitude,
        String timezone,

        @JsonProperty("admin1")
        String estado,

        String country,
        @JsonProperty("country_code")
        String codigoPais
) {
}
