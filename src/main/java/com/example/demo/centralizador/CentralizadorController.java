package com.example.demo.centralizador;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/centralizador")
@RequiredArgsConstructor
public class CentralizadorController {

    private final CentralizadorService centralizadorService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<CentralizadorRespostaDTO> consultar( @PathVariable String cnpj ) {
        return ResponseEntity.ok( centralizadorService.consultar(cnpj));
    }
}

