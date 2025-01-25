package com.example.portfolio_backend.service;

import com.example.portfolio_backend.entity.Stock;
import com.example.portfolio_backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockPriceService stockPriceService;

    @Autowired
    public StockService(StockRepository stockRepository, StockPriceService stockPriceService) {
        this.stockRepository = stockRepository;
        this.stockPriceService = stockPriceService;
    }


    // Get all stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // Get stock by ID
    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    // Add new stock
    public Stock addStock(Stock stock) {
        // Fetch the current price of the stock using StockPriceService
        Double currentPrice = stockPriceService.getStockPrice(stock.getTicker());

        // Set the current price (add a new field for currentPrice if not already there)
        stock.setCurrentPrice(BigDecimal.valueOf(currentPrice));

        return stockRepository.save(stock);
    }

    // Update existing stock
    public Stock updateStock(Long id, Stock stockDetails) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setName(stockDetails.getName());
        stock.setTicker(stockDetails.getTicker());
        stock.setQuantity(stockDetails.getQuantity());
        stock.setBuyPrice(stockDetails.getBuyPrice());
        return stockRepository.save(stock);
    }

    // Delete stock by ID
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    // Calculate the portfolio value dynamically using real-time stock prices
    public Double calculatePortfolioValue() {
        List<Stock> stocks = stockRepository.findAll();
        double totalValue = 0;

        for (Stock stock : stocks) {
            Double realTimePrice = stockPriceService.getStockPrice(stock.getTicker());
            totalValue += realTimePrice * stock.getQuantity();  // Multiply price by quantity (1 in this case)
        }
        return totalValue;
    }

    // Get the top-performing stock by performance metric
    public String getTopPerformingStock() {
        List<Stock> stocks = stockRepository.findAll();

        OptionalDouble maxPerformance = stocks.stream()
                .mapToDouble(Stock::getPerformance) // Assuming a getPerformance method in Stock entity
                .max();

        return maxPerformance.isPresent()
                ? stocks.stream()
                .filter(stock -> stock.getPerformance() == maxPerformance.getAsDouble())
                .findFirst()
                .map(Stock::getTicker)
                .orElse("No stock found")
                : "No stock found";
    }

    // Get the portfolio distribution
    public Map<String, Double> getPortfolioDistribution() {
        List<Stock> stocks = stockRepository.findAll();
        double totalValue = calculatePortfolioValue();
        Map<String, Double> distribution = new HashMap<>();

        for (Stock stock : stocks) {
            double stockValue = stock.getQuantity() * stock.getBuyPrice().doubleValue();
            distribution.put(stock.getTicker(), (stockValue / totalValue) * 100);
        }

        return distribution;
    }
}