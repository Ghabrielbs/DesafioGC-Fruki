package com.example.demo.feriado.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FeriadoDTO(
        String name,
        LocalDate date) {
}
