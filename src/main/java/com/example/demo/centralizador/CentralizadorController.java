package com.example.demo.centralizador;

import com.example.demo.centralizador.dto.CentralizadorRespostaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/centralizador")
@RequiredArgsConstructor
public class CentralizadorController implements CentralizadorDocumentacao {

    private final CentralizadorService centralizadorService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<CentralizadorRespostaDTO> consultar( @PathVariable String cnpj ) {
        return ResponseEntity.ok( centralizadorService.consultar(cnpj));
    }
}

