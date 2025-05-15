import java.util.Map;

public class models package com.meuapp.models;

import java.util.Map;

public record ExchangeRateResponse(
        String base_code,
        Map<String, Double> conversion_rates
) {}{
}
