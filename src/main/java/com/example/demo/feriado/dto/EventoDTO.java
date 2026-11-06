package com.example.demo.feriado.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Evento ou feriado aplicável à localidade do cliente.")
public record EventoDTO(
        @Schema(description = "Nome do evento/feriado", example = "Proclamação da República")
        String name,
        @Schema(description = "Data local do evento", example = "2026-11-15", type = "string", format = "date")
        LocalDate localDate
) {
}
