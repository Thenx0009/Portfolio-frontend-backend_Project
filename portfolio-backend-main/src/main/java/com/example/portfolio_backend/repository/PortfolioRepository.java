package com.example.portfolio_backend.repository;

import com.example.portfolio_backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
