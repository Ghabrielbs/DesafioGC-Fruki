package com.example.demo.centralizador;

import com.example.demo.centralizador.dto.CentralizadorRespostaDTO;
import com.example.demo.cliente.dto.ClienteResponseDTO;
import com.example.demo.cliente.ClienteService;
import com.example.demo.clima.dto.ClimaDoClienteDTO;
import com.example.demo.clima.ClienteClimaService;
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

        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);

        ClimaDoClienteDTO clima = clienteClimaService.consultar(
                        cliente.cidade(),
                        cliente.estado(),
                        cnpj );

        List<EventoDTO> eventos = feriadoService.getFeriadoByMunicipio(cliente);

        // 4. Combina os resultados
        return new CentralizadorRespostaDTO(cliente, clima, eventos);
    }
}
