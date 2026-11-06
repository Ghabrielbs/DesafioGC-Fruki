package com.example.demo.feriado;

import com.example.demo.cliente.dto.ClienteResponseDTO;
import com.example.demo.feriado.dto.EventoDTO;
import com.example.demo.feriado.dto.ResponseEventoDTO;
import com.example.demo.feriado.dto.RespostaFeriadosDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class FeriadoService {
    private final RestClient client;

    public FeriadoService(RestClient.Builder builder) {
        this.client = builder
                .baseUrl("https://feriados-fruki.onrender.com/v1")
                .build();
    }

    public List<EventoDTO> getFeriadoByMunicipio(ClienteResponseDTO cliente) {
        LocalDate data = LocalDate.now();
        LocalDate dateEnd = data.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ResponseEventoDTO responseEventos = Objects.requireNonNull(
                client.get()
                        .uri("/impact-events/location/" + formatCodeMunicipio(cliente.estado(), cliente.cidade()) + "?startDate=" + data.format(formatter) + "&endDate=" + dateEnd.format(formatter))
                        .retrieve()
                        .body(ResponseEventoDTO.class)
        );

        RespostaFeriadosDTO responseFeriado = Objects.requireNonNull(
                client.get()
                        .uri("/holidays/location/" + formatCodeMunicipio(cliente.estado(), cliente.cidade()) + "?startDate=" + data.format(formatter) + "&endDate=" + dateEnd.format(formatter))
                        .retrieve()
                        .body(RespostaFeriadosDTO.class)
        );

        List<EventoDTO> eventos = Stream.concat(
                Optional.ofNullable(responseEventos.date())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(evento -> new EventoDTO(
                                evento.name(),
                                evento.localDate()
                        )),
                Optional.ofNullable(responseFeriado.data())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(feriado -> new EventoDTO(
                                feriado.name(),
                                feriado.date()
                        ))
        ).toList();

        return eventos;
    }

    private String formatCodeMunicipio(String estado, String municipio){
        String codeMunicipio = estado + " " + municipio;
        codeMunicipio = Normalizer
                .normalize(codeMunicipio, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return codeMunicipio.replace(" ", "-");
    }
}
