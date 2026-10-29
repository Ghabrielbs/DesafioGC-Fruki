package com.example.demo.clima.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClimaService {

    private static final int QUANTIDADE_DIAS = 7;

    private final RestClient openMeteo;

    public ClimaService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(10_000);
        factory.setReadTimeout(30_000);

        this.openMeteo = RestClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .requestFactory(factory)
                .build();
    }

    public ResumoClima consultar(
            double latitude,
            double longitude,
            String timezone) {

        validarCoordenadas(latitude, longitude);

        if (timezone == null || timezone.isBlank()) {// verifica se o fuso é uma informação válida
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Fuso horário não informado"
            );
        }

        RespostaOpenMeteo resposta;

        // Busca os dados meteorológicos na API externa.
        try {
            resposta = openMeteo.get()
                    .uri(uri -> uri
                            .path("/v1/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("daily","temperature_2m_mean,weather_code")
                            .queryParam("temperature_unit", "celsius")
                            .queryParam("timezone", timezone)
                            .queryParam("forecast_days", QUANTIDADE_DIAS)
                            .build())
                    .retrieve()
                    .body(RespostaOpenMeteo.class);

        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Falha ao consultar a previsão do tempo",e);
        }

        if (resposta == null || resposta.daily() == null) {
            throw respostaInvalida();
        }

        DadosDiarios dados = resposta.daily();

        validarDadosDiarios(dados);

        List<DiaClima> dias = new ArrayList<>();
        double somaTemperaturas = 0;

        // As três listas usam a mesma posição para representar um dia.
        for (int i = 0; i < QUANTIDADE_DIAS; i++) {
            String dataRecebida = dados.datas().get(i);
            Double temperatura = dados.temperaturas().get(i);
            Integer codigo = dados.codigos().get(i);

            if (dataRecebida == null
                    || temperatura == null
                    || !Double.isFinite(temperatura)
                    || codigo == null) {

                throw respostaInvalida();
            }

            LocalDate data;

            try {
                data = LocalDate.parse(dataRecebida);
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "A API retornou uma data inválida",
                        e
                );
            }

            // Confere se os sete dias são consecutivos.
            if (i > 0 && !data.equals(
                    dias.get(i - 1).data().plusDays(1))) {

                throw respostaInvalida();
            }

            somaTemperaturas += temperatura;

            dias.add(new DiaClima(
                    data,
                    temperatura,
                    descreverCondicao(codigo)
            ));
        }

        return new ResumoClima(
        "PREVISAO",
        "°C",
        dias.get(0).data(),
        dias.get(QUANTIDADE_DIAS - 1).data(),
        somaTemperaturas / QUANTIDADE_DIAS,
        List.copyOf(dias)
        );
    }

    private void validarCoordenadas(double latitude, double longitude) {

        if (!Double.isFinite(latitude)
                || !Double.isFinite(longitude)
                || latitude < -90
                || latitude > 90
                || longitude < -180
                || longitude > 180) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Coordenadas inválidas");
        }
    }

    private void validarDadosDiarios(DadosDiarios dados) {
        if (dados.datas() == null
                || dados.temperaturas() == null
                || dados.codigos() == null
                || dados.datas().size() != QUANTIDADE_DIAS
                || dados.temperaturas().size() != QUANTIDADE_DIAS
                || dados.codigos().size() != QUANTIDADE_DIAS) {

            throw respostaInvalida();
        }
    }

    private ResponseStatusException respostaInvalida() {
        return new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "A Open-Meteo não retornou sete dias completos e válidos"
        );
    }

    private String descreverCondicao(int codigo) {
        return switch (codigo) {
            case 0 -> "Céu limpo";
            case 1 -> "Predominantemente limpo";
            case 2 -> "Parcialmente nublado";
            case 3 -> "Nublado";
            case 45, 48 -> "Nevoeiro";
            case 51, 53, 55 -> "Garoa";
            case 56, 57 -> "Garoa congelante";
            case 61, 63, 65 -> "Chuva";
            case 66, 67 -> "Chuva congelante";
            case 71, 73, 75, 77 -> "Neve";
            case 80, 81, 82 -> "Pancadas de chuva";
            case 85, 86 -> "Pancadas de neve";
            case 95 -> "Trovoadas";
            case 96, 99 -> "Trovoadas com granizo";
            default -> "Código desconhecido: " + codigo;
        };
    }

    // Estruturas usadas para interpretar o JSON da Open-Meteo.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RespostaOpenMeteo(
            String timezone,
            DadosDiarios daily
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DadosDiarios(
            @JsonProperty("time")
            List<String> datas,

            @JsonProperty("temperature_2m_mean")
            List<Double> temperaturas,

            @JsonProperty("weather_code")
            List<Integer> codigos
    ) {}

    // Estruturas retornadas pelo serviço.
    public record DiaClima(
            LocalDate data,
            double temperaturaMedia,
            String condicao
    ) {}

    public record ResumoClima(
        String tipoDado,
        String unidadeTemperatura,
        LocalDate inicio,
        LocalDate fim,
        double temperaturaMediaPeriodo,
        List<DiaClima> dias
) {}
}