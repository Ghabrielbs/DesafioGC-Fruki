package com.example.demo.feriado;

import com.example.demo.cliente.ClienteResponseDTO;
import com.example.demo.cliente.ClienteService;
import com.example.demo.feriado.dto.EventoDTO;
import com.example.demo.feriado.dto.FeriadoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/eventos")
public class FeriadoController {
    private final FeriadoService feriadoService;
    private final ClienteService clienteService;

    @GetMapping("/feriados/{cnpj}")
    public ResponseEntity<List<EventoDTO>> buscarCnpj(@PathVariable String cnpj) {
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(feriadoService.getFeriadoByMunicipio(cliente));

    }
}
