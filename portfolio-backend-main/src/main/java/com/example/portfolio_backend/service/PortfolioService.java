package com.example.portfolio_backend.service;

import com.example.portfolio_backend.entity.Portfolio;
import com.example.portfolio_backend.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;


    @Autowired
    private StockPriceService stockPriceService;

    public Portfolio addPortfolioEntry(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> getAllPortfolioEntries() {
        return portfolioRepository.findAll();
    }

    // Get portfolio entry by ID
    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio entry not found"));
    }

    // Update portfolio entry by ID
    public Portfolio updatePortfolio(Long id, Portfolio portfolioDetails) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio entry not found"));

        portfolio.setName(portfolioDetails.getName());
        portfolio.setTicker(portfolioDetails.getTicker());
        portfolio.setQuantity(portfolioDetails.getQuantity());
        portfolio.setBuyPrice(portfolioDetails.getBuyPrice());
        portfolio.setCurrentPrice(portfolioDetails.getCurrentPrice());

        return portfolioRepository.save(portfolio);
    }

    // Delete portfolio entry by ID
    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    // Calculate the total portfolio value dynamically using real-time stock prices
    public Double calculatePortfolioValue() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        double totalValue = 0;

        for (Portfolio portfolio : portfolios) {
            Double realTimePrice = stockPriceService.getStockPrice(portfolio.getTicker());
            // Multiply real-time price by quantity to get the total value for this stock in the portfolio
            totalValue += realTimePrice * portfolio.getQuantity();
        }
        return totalValue;
    }

    // Get the top-performing stock by performance metric (percentage increase/decrease)
    public String getTopPerformingStock() {
        List<Portfolio> portfolios = portfolioRepository.findAll();

        OptionalDouble maxPerformance = portfolios.stream()
                .mapToDouble(portfolio -> {
                    Double realTimePrice = stockPriceService.getStockPrice(portfolio.getTicker());
                    return calculatePerformance(portfolio.getBuyPrice(), realTimePrice);
                })
                .max();

        return maxPerformance.isPresent()
                ? portfolios.stream()
                .filter(portfolio -> calculatePerformance(portfolio.getBuyPrice(),
                        stockPriceService.getStockPrice(portfolio.getTicker())) == maxPerformance.getAsDouble())
                .findFirst()
                .map(Portfolio::getTicker)
                .orElse("No stock found")
                : "No stock found";
    }

    // Helper method to calculate the performance of a stock
    private double calculatePerformance(BigDecimal buyPrice, double realTimePrice) {
        return ((realTimePrice - buyPrice.doubleValue()) / buyPrice.doubleValue()) * 100;
    }

    // Get the portfolio distribution based on the stock value
    public Map<String, Double> getPortfolioDistribution() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        double totalValue = calculatePortfolioValue();
        Map<String, Double> distribution = new HashMap<>();

        for (Portfolio portfolio : portfolios) {
            double realTimePrice = stockPriceService.getStockPrice(portfolio.getTicker());
            double stockValue = portfolio.getQuantity() * realTimePrice;  // Calculate stock value using real-time price
            distribution.put(portfolio.getTicker(), (stockValue / totalValue) * 100);
        }

        return distribution;
    }
}