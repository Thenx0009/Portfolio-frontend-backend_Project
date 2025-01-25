package com.example.portfolio_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class StockPriceService {

    private static final String API_URL = "https://finnhub.io/api/v1/quote?symbol=%s&token=%s";
    private static final String API_KEY = "cu3rauhr01qp6s4j77b0cu3rauhr01qp6s4j77bg"; // Replace with your Finnhub API key

    public double getStockPrice(String ticker) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(API_URL, ticker, API_KEY);
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("c")) { // 'c' is the current price field
                return Double.parseDouble(response.get("c").toString());
            }
            throw new RuntimeException("Invalid response for ticker: " + ticker);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch real-time price for ticker: " + ticker + ". Reason: " + e.getMessage(), e);
        }
    }
}