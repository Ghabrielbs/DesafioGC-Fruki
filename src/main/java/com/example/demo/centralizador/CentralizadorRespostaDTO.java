
package com.example.demo.centralizador;

import com.example.demo.cliente.ClienteResponseDTO;
import com.example.demo.clima.dtos.ClimaDoClienteDTO;
import com.example.demo.feriado.dto.EventoDTO;

import java.util.List;

public record CentralizadorRespostaDTO(
        ClienteResponseDTO cliente,
        ClimaDoClienteDTO clima,
        List<EventoDTO> eventos
) {
}
