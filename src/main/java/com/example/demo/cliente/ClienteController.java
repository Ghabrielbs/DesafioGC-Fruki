package com.example.demo.cliente;

import com.example.demo.cliente.dtos.ClienteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;


    @GetMapping("/{cnpj}")
    public ResponseEntity<ClienteResponseDTO>buscarCnpj(@PathVariable String cnpj ){
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        return  ResponseEntity.status(HttpStatus.OK).body(cliente);

    }

}
