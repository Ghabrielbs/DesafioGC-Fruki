package com.example.demo.cliente.dtos;

public record ClienteResponseDTO(
        Long id,
        String cnpj,
        String cidade,
        String estado
) {
}
