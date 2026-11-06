
package com.example.demo.clima;

import com.example.demo.clima.dto.Localizacao;
import com.example.demo.clima.dto.RespostaGeocoding;

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
        RespostaGeocoding resposta;

        try {
            resposta = geocoding.get()
                    .uri(uri -> uri
                            .path("/v1/search")
                            .queryParam("name", cidade)
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
        String estadoNormalizado = normalizar(estado);

        List<Localizacao> locais = resposta.results().stream()
                .filter(local -> local != null
                        && local.name() != null
                        && cidadeNormalizada.equals(
                        normalizar(local.name())
                )
                        && "BR".equalsIgnoreCase(local.codigoPais())
                        && estadoCompativel(
                        estadoNormalizado,
                        local.estado()
                )
                        && local.latitude() != null
                        && local.longitude() != null
                        && Double.isFinite(local.latitude())
                        && Double.isFinite(local.longitude())
                        && local.timezone() != null
                        && !local.timezone().isBlank())
                .toList();

        if (locais.isEmpty()) {
            throw cidadeNaoEncontrada();
        }


        return locais.get(0);

    }

    private ResponseStatusException cidadeNaoEncontrada() {
        return new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Não foi possível localizar a cidade e o estado cadastrados"
        );
    }

    private static boolean estadoCompativel(
            String estadoInformado,
            String estadoRetornado
    ) {
        if (estadoRetornado == null) {
            return false;
        }

        String estado = normalizar(estadoRetornado);

        return estadoInformado.equals(estado)
                || estadoInformado.equals(
                normalizar(siglaEstado(estadoRetornado))
        );
    }

    private static String siglaEstado(String estado) {
        return switch (normalizar(estado)) {
            case "acre" -> "AC";
            case "alagoas" -> "AL";
            case "amapa" -> "AP";
            case "amazonas" -> "AM";
            case "bahia" -> "BA";
            case "ceara" -> "CE";
            case "distrito federal" -> "DF";
            case "espirito santo" -> "ES";
            case "goias" -> "GO";
            case "maranhao" -> "MA";
            case "mato grosso" -> "MT";
            case "mato grosso do sul" -> "MS";
            case "minas gerais" -> "MG";
            case "para" -> "PA";
            case "paraiba" -> "PB";
            case "parana" -> "PR";
            case "pernambuco" -> "PE";
            case "piaui" -> "PI";
            case "rio de janeiro" -> "RJ";
            case "rio grande do norte" -> "RN";
            case "rio grande do sul" -> "RS";
            case "rondonia" -> "RO";
            case "roraima" -> "RR";
            case "santa catarina" -> "SC";
            case "sao paulo" -> "SP";
            case "sergipe" -> "SE";
            case "tocantins" -> "TO";
            default -> "";
        };
    }

    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        return Normalizer.normalize(
                        texto,
                        Normalizer.Form.NFD
                )
                .replaceAll("\\p{M}+", "")
                .trim()
                .toLowerCase(Locale.ROOT);
    }
}
