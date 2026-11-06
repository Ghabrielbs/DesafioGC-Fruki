package com.example.demo.centralizador;

import com.example.demo.centralizador.dto.CentralizadorRespostaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Centralizador", description = "Consulta consolidada de informações do cliente, clima e eventos.")
public interface CentralizadorDocumentacao {

    @Operation(
            summary = "Consultar dados consolidados por CNPJ",
            description = "Retorna em uma única resposta os dados do cliente, a previsão do tempo e os eventos/feriados relacionados dos proximos 7 dias."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CentralizadorRespostaDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "CNPJ inválido", content = @Content),
            @ApiResponse(responseCode = "502", description = "Falha ao consultar integrações externas", content = @Content)
    })
    ResponseEntity<CentralizadorRespostaDTO> consultar(
            @Parameter(description = "CNPJ do cliente para consulta consolidada", required = true, example = "12345678000199")
            String cnpj
    );
}
