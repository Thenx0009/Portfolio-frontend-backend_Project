package com.example.portfolio_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ticker;
    private int quantity;
    private BigDecimal buyPrice;
    private BigDecimal currentPrice;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPerformance() {
        if (currentPrice != null && buyPrice != null && buyPrice.doubleValue() != 0) {
            return ((currentPrice.doubleValue() - buyPrice.doubleValue()) / buyPrice.doubleValue()) * 100;
        }
        return 0.0;
    }
}
