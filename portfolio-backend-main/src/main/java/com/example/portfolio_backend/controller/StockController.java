package com.example.portfolio_backend.controller;

import com.example.portfolio_backend.entity.Portfolio;
import com.example.portfolio_backend.entity.Stock;
import com.example.portfolio_backend.service.PortfolioService;
import com.example.portfolio_backend.service.StockPriceService;
import com.example.portfolio_backend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:5173") // Enable CORS for React+vite frontend
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private PortfolioService portfolioService;


    @Autowired
    private StockPriceService stockPriceService;

    @PostMapping
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        Stock savedStock = stockService.addStock(stock);
        return ResponseEntity.ok(savedStock);
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stockDetails) {
        Stock updatedStock = stockService.updateStock(id, stockDetails);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    //    @GetMapping("/dashboard")
//    public ResponseEntity<Map<String, Object>> getDashboardData() {
//        double totalValue = stockService.calculatePortfolioValue();
//        String topStock = stockService.getTopPerformingStock();
//        Map<String, Double> distribution = stockService.getPortfolioDistribution();
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("totalValue", totalValue);
//        response.put("topStock", topStock);
//        response.put("distribution", distribution);
//
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        // Use PortfolioService to calculate the total value of the portfolio
        double totalValue = portfolioService.calculatePortfolioValue();

        // Use PortfolioService to get the top-performing stock from the portfolio
        String topStock = portfolioService.getTopPerformingStock();

        // Use PortfolioService to get the portfolio distribution
        Map<String, Double> distribution = portfolioService.getPortfolioDistribution();

        // Prepare the response data
        Map<String, Object> response = new HashMap<>();
        response.put("totalValue", totalValue);
        response.put("topStock", topStock);
        response.put("distribution", distribution);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/real-time-price/{ticker}")
    public ResponseEntity<Double> getRealTimePrice(@PathVariable String ticker) {
        double price = stockPriceService.getStockPrice(ticker);
        return ResponseEntity.ok(price);
    }

    @PostMapping("/portfolio")
    public ResponseEntity<Portfolio> addPortfolioEntry(@RequestBody Portfolio portfolio) {
        Portfolio savedPortfolio = portfolioService.addPortfolioEntry(portfolio);
        return ResponseEntity.ok(savedPortfolio);
    }

    @GetMapping("/portfolio")
    public ResponseEntity<List<Portfolio>> getAllPortfolioEntries() {
        List<Portfolio> portfolioEntries = portfolioService.getAllPortfolioEntries();
        return ResponseEntity.ok(portfolioEntries);
    }

}