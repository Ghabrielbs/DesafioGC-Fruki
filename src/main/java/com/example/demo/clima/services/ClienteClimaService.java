package com.example.demo.clima.services;

import com.example.demo.clima.dtos.ClimaDoCliente;
import com.example.demo.clima.dtos.Localizacao;

import org.springframework.stereotype.Service;

//usa os dados recebidos para consultar a previsão.
@Service
public class ClienteClimaService {


    private final GeocodingClient geocodingClient;
    private final ClimaService climaService;


    public ClienteClimaService(GeocodingClient geocodingClient,ClimaService climaService) {
        this.geocodingClient = geocodingClient;
        this.climaService = climaService;
    }

    public ClimaDoCliente consultar(String CidadeCliente, String EstadoCliente, String cnpj) {

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

        return new ClimaDoCliente(
                cnpj,
                cidade,
                estado,
                previsao
        );
    }
}