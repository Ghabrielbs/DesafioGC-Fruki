package com.example.demo.clima.dtos;

import com.example.demo.clima.services.ClimaService;

public record ClimaDoClienteDTO(
        String cnpj,
        String cidade,
        String estado,
        ClimaService.ResumoClima previsao
) 
{}