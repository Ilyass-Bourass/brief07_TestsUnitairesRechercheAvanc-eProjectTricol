package com.example.demo.repository;

import com.example.demo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Boolean existsStockByNumeroLot(String numeroLot);
}
