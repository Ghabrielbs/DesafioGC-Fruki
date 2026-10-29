package com.example.demo.clima.services;

import com.example.demo.clima.dtos.ClimaDoClienteDTO;
import com.example.demo.clima.dtos.Localizacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//usa os dados recebidos para consultar a previsão.
@Service
@RequiredArgsConstructor
public class ClienteClimaService {

    private final GeocodingClient geocodingClient;
    private final ClimaService climaService;


    public ClimaDoClienteDTO consultar(String CidadeCliente, String EstadoCliente, String cnpj) {

        String cidade = CidadeCliente;
        String estado = EstadoCliente;

        // Busca as coordenadas da cidade.
        Localizacao local = geocodingClient.buscar(cidade, estado);

        // Consulta a previsão e calcula a média do período.
        ClimaService.ResumoClima previsao = climaService.consultar(
                local.latitude(),
                local.longitude(),
                local.timezone()
        );

        return new ClimaDoClienteDTO(
                cnpj,
                cidade,
                estado,
                previsao
        );
    }
}