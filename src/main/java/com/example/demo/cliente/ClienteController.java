package com.example.demo.cliente;


import com.example.demo.clima.dtos.ClimaDoCliente;
import com.example.demo.clima.services.ClienteClimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteClimaService clienteClimaService;


    @GetMapping("/{cnpj}")
    public ResponseEntity<ClienteResponseDTO> buscarCnpj(@PathVariable String cnpj) {
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);

    }

    //faz a previsão do clima buscando o cnpj
    @GetMapping("/clima/{id}")
    public ResponseEntity<ClimaDoCliente> buscarClima(@PathVariable String cnpj) {
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        ClimaDoCliente previsao = clienteClimaService.consultar(cliente.cidade(), cliente.estado(), cliente.cnpj());

        return ResponseEntity.ok(previsao);
    }

}
