
package com.example.demo.centralizador.dto;

import com.example.demo.cliente.dto.ClienteResponseDTO;
import com.example.demo.clima.dto.ClimaDoClienteDTO;
import com.example.demo.feriado.dto.EventoDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resposta consolidada com dados do cliente, clima e eventos.")
public record CentralizadorRespostaDTO(
        @Schema(description = "Dados do cliente")
        ClienteResponseDTO cliente,
        @Schema(description = "Previsão do tempo do cliente")
        ClimaDoClienteDTO clima,
        @Schema(description = "Lista de eventos/feriados aplicáveis ao cliente")
        List<EventoDTO> eventos
) {
}
