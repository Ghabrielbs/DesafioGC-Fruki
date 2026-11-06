package com.example.demo.feriado;

import com.example.demo.cliente.dto.ClienteResponseDTO;
import com.example.demo.cliente.ClienteService;
import com.example.demo.feriado.dto.EventoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/eventos")
public class FeriadoController implements FeriadoDocumentacao {

    private final FeriadoService feriadoService;
    private final ClienteService clienteService;

    @Override
    @GetMapping("/{cnpj}")
    public ResponseEntity<List<EventoDTO>> buscarEventosFeriados(@PathVariable String cnpj) {
        ClienteResponseDTO cliente = clienteService.buscarCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(feriadoService.getFeriadoByMunicipio(cliente));
    }
}
