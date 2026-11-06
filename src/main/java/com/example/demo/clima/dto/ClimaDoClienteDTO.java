package com.example.demo.clima.dto;

import com.example.demo.clima.ClimaService;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Previsão do tempo associada ao cliente.")
public record ClimaDoClienteDTO(
        @Schema(description = "CNPJ do cliente consultado", example = "12345678000199")
        String cnpj,
        @Schema(description = "Cidade usada na consulta do clima", example = "Lajeado")
        String cidade,
        @Schema(description = "UF usada na consulta do clima", example = "RS")
        String estado,
        @Schema(description = "Resumo da previsão do tempo retornado pelo serviço de clima")
        ClimaService.ResumoClima previsao
) {
}