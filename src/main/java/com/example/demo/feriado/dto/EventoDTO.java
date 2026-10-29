package com.example.demo.feriado.dto;

import java.time.LocalDate;

public record EventoDTO(
        String name,
        LocalDate localDate
) {
}
