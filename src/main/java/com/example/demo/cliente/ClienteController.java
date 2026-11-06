package com.example.demo.cliente;

import com.example.demo.cliente.dto.ClienteResponseDTO;
import com.example.demo.clima.dto.ClimaDoClienteDTO;
import com.example.demo.clima.ClienteClimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController implements ClienteDocumentacao {

    private final ClienteService clienteService;
    private final ClienteClimaService clienteClimaService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<ClienteResponseDTO> buscarCnpj(@PathVariable String cnpj) {
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);

    }

    @GetMapping("/clima/{cnpj}")
    public ResponseEntity<ClimaDoClienteDTO> buscarClima(@PathVariable String cnpj) {
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        ClimaDoClienteDTO previsao = clienteClimaService.consultar(cliente.cidade(), cliente.estado(), cliente.cnpj());

        return ResponseEntity.ok(previsao);
    }
}
