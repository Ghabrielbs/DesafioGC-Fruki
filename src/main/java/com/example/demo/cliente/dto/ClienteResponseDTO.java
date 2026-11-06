package com.example.demo.cliente.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados básicos do cliente retornados pela API.")
public record ClienteResponseDTO(
        @Schema(description = "Identificador interno do cliente", example = "1")
        Long id,
        @Schema(description = "CNPJ do cliente", example = "12345678000199")
        String cnpj,
        @Schema(description = "Cidade do cliente", example = "Lajeado")
        String cidade,
        @Schema(description = "UF do cliente", example = "RS")
        String estado
) {
}
