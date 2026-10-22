package com.example.demo.cliente;

import com.example.demo.cliente.dtos.ClienteResponseDTO;
import com.example.demo.clima.services.ClienteClimaService;
import com.example.demo.clima.dtos.ClimaDoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//http://localhost:8080/cliente/00110569000100/clima -> teste

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteClimaService clienteClimaService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<ClienteResponseDTO>buscarCnpj(@PathVariable String cnpj ){
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        return  ResponseEntity.status(HttpStatus.OK).body(cliente);

    }

    //faz a previsão do clima buscando o cnpj
    @GetMapping("/{cnpj}/clima")
    public ResponseEntity<ClimaDoCliente> buscarClima(@PathVariable String cnpj) {
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        ClimaDoCliente previsao = clienteClimaService.consultar(cliente.cidade(),cliente.estado(), cliente.cnpj());

        return ResponseEntity.ok(previsao);
    }
}
