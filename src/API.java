package com.meuapp.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meuapp.models.ExchangeRateResponse;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class ApiClient {

    private final String apiKey;
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Gson gson;

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
            this.apiKey = props.getProperty("api.key");
            this.baseUrl = props.getProperty("api.url");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o config.properties");
        }
    }

    public ExchangeRateResponse getRates(String baseCurrency) {
        String url = baseUrl + apiKey + "/latest/" + baseCurrency;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), ExchangeRateResponse.class);
            } else {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                throw new RuntimeException("Erro da API: " + json.get("error-type").getAsString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao acessar a API");
        }
    }
}
