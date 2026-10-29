package com.example.demo.centralizador;

import com.example.demo.cliente.ClienteResponseDTO;
import com.example.demo.cliente.ClienteService;
import com.example.demo.clima.dtos.ClimaDoClienteDTO;
import com.example.demo.clima.services.ClienteClimaService;
import com.example.demo.feriado.FeriadoService;
import com.example.demo.feriado.dto.EventoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CentralizadorService {

    private final ClienteService clienteService;

    private final FeriadoService feriadoService;

    private final ClienteClimaService clienteClimaService;


    public CentralizadorRespostaDTO consultar(String cnpj) {

        // 1. Busca o cliente pelo CNPJ
        ClienteResponseDTO cliente =
                clienteService.buscarCnpj(cnpj);

        // 2. Consulta o clima dos próximos dias
        ClimaDoClienteDTO clima =
                clienteClimaService.consultar(
                        cliente.cidade(),
                        cliente.estado(),
                        cnpj
                );

        // 3. Busca os eventos e feriados da região
        List<EventoDTO> eventos =
                feriadoService.getFeriadoByMunicipio(cliente);

        // 4. Combina os resultados
        return new CentralizadorRespostaDTO(cliente, clima, eventos);
    }
}
