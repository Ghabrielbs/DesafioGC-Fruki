package com.example.demo.clima.services;

import com.example.demo.clima.dtos.Localizacao;
import com.example.demo.clima.dtos.RespostaGeocoding;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

@Component
public class GeocodingClient {

    private final RestClient geocoding;

    public GeocodingClient() {
        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(5_000);
        factory.setReadTimeout(20_000);

        this.geocoding = RestClient.builder()
                .baseUrl("https://geocoding-api.open-meteo.com")
                .requestFactory(factory)
                .build();
    }

    public Localizacao buscar(String cidade, String estado) {
        String pesquisa = cidade + ", " + estado;

        RespostaGeocoding resposta;

        try {
            resposta = geocoding.get()
                    .uri(uri -> uri
                            .path("/v1/search")
                            .queryParam("name", pesquisa)
                            .queryParam("countryCode", "BR")
                            .queryParam("language", "pt")
                            .queryParam("count", 100)
                            .build())
                    .retrieve()
                    .body(RespostaGeocoding.class);

        } catch (RestClientException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Falha ao consultar a localização da cidade",
                    e
            );
        }

        if (resposta == null || resposta.results() == null) {
            throw cidadeNaoEncontrada();
        }

        String cidadeNormalizada = normalizar(cidade);

        List<Localizacao> locais = resposta.results().stream()
                .filter(local -> local != null
                        && cidadeNormalizada.equals(normalizar(local.name())))
                .toList();

        if (locais.isEmpty()) {
            throw cidadeNaoEncontrada();
        }

        if (locais.size() > 1) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_CONTENT,
                    "Mais de uma localização corresponde à cidade cadastrada"
            );
        }

        Localizacao local = locais.get(0);

        if (local.latitude() == null
                || local.longitude() == null
                || !Double.isFinite(local.latitude())
                || !Double.isFinite(local.longitude())
                || local.timezone() == null
                || local.timezone().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "A localização retornou dados incompletos"
            );
        }

        return local;
    }

    private ResponseStatusException cidadeNaoEncontrada() {
        return new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Não foi possível localizar a cidade cadastrada"
        );
    }

    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .trim()
                .toLowerCase(Locale.ROOT);
    }
}
