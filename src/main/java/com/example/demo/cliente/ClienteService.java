package com.example.demo.cliente;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private  final ClienteRepository clienteRepository;

    public ClienteResponseDTO buscarCnpj(String cnpj) {
        Cliente cliente = clienteRepository.findByCnpj(cnpj)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado")
                );

        return new ClienteResponseDTO(
                    cliente.getId(),
                    cliente.getCnpj(),
                    cliente.getCidade(),
                    cliente.getEstado()
            );
    }


}
