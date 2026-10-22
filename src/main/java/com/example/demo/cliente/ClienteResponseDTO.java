package com.example.demo.cliente;

public record ClienteResponseDTO(
        Long id,
        String cnpj,
        String cidade,
        String estado
) {
}
