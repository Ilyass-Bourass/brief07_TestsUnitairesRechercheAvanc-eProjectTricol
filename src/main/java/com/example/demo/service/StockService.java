package com.example.demo.service;

import com.example.demo.dto.stock.RequestStockDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.Stock;

import java.time.LocalDateTime;
import java.util.List;

public interface StockService {
    List<ResponseStockDTO> getAllStocks();
    List<ResponseStockDTO> getStocksByProduit(Long produitId);

}
