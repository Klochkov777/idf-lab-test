package com.klochkov.idflabtest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig {
    @Value("${currency.symbols}")
    private String symbols;
    @Value("${api-key.twelvedata}")
    private String apiKeyTwelwedata;

    /**
     * Method for getting bean symbols (currencies).
     *
     * @return - list currencies.
     */
    @Bean
    public List<String> symbols() {
        return Arrays.asList(symbols.split(",\\s*"));
    }
    /**
     * Method for getting bean apiKeyTwelwedata.
     *
     * @return - apiKeyTwelwedata.
     */
    @Bean
    public String apiKeyTwelwedata() {
        return apiKeyTwelwedata;
    }
}
