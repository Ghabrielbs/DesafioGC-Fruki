package com.example.demo.feriado;

import com.example.demo.feriado.dto.EventoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Eventos", description = "Consulta de feriados e eventos relacionados à localização do cliente.")
public interface FeriadoDocumentacao {

    @Operation(
            summary = "Listar feriados e eventos por CNPJ do cliente",
            description = "Busca o cliente pelo CNPJ e retorna os feriados/eventos aplicáveis ao município do cliente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos encontrados com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventoDTO.class)))
            ),
            @ApiResponse(responseCode = "400", description = "CNPJ inválido", content = @Content)
    })
    ResponseEntity<List<EventoDTO>> buscarEventosFeriados(
            @Parameter(description = "CNPJ do cliente usado para identificar o município", required = true, example = "12345678000199")
            String cnpj
    );
}
