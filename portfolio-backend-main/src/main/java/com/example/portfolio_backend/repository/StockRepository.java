package com.example.portfolio_backend.repository;

import com.example.portfolio_backend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}