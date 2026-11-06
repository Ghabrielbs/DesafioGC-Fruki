package com.example.demo.cliente;

import com.example.demo.cliente.dto.ClienteResponseDTO;
import com.example.demo.clima.dto.ClimaDoClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Clientes", description = "Operações relacionadas à consulta de clientes e previsão do tempo por CNPJ.")
public interface ClienteDocumentacao {

    @Operation(
            summary = "Buscar cliente por CNPJ",
            description = "Retorna os dados cadastrais do cliente a partir do CNPJ informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "CNPJ inválido", content = @Content)
    })
    ResponseEntity<ClienteResponseDTO> buscarCnpj(
            @Parameter(description = "CNPJ do cliente", required = true, example = "12345678000199")
            String cnpj
    );

    @Operation(
            summary = "Consultar previsão do tempo do cliente",
            description = "Busca o cliente pelo CNPJ e retorna a previsão do tempo com base na cidade e estado do cliente dos proximos 7 dias."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Previsão consultada com sucesso",
                    content = @Content(schema = @Schema(implementation = ClimaDoClienteDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "CNPJ inválido", content = @Content),
            @ApiResponse(responseCode = "502", description = "Falha ao consultar serviço de clima", content = @Content)
    })
    ResponseEntity<ClimaDoClienteDTO> buscarClima(
            @Parameter(description = "CNPJ do cliente usado para localizar cidade e estado", required = true, example = "12345678000199")
            String cnpj
    );
}
