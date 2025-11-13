package com.example.demo.service.impl;

import com.example.demo.dto.stock.RequestStockDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.Stock;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;


    @Override
    public List<ResponseStockDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        List<ResponseStockDTO> responseStockDTOS =stocks.stream().map(stockMapper::toResponseStockDTO).toList();
        return responseStockDTOS;
    }

    @Override
    public List<ResponseStockDTO> getStocksByProduit(Long produitId) {
        return getAllStocks().stream().filter(p -> p.getProduitId() == produitId)
                .sorted(Comparator.comparing(ResponseStockDTO::getDateEntree)).
                toList();
    }

    @Override
    public double valorosiationStocksTotal() {
        return getAllStocks().stream().mapToDouble(stock->stock.getQuantite()*stock.getPrixAchatUnitaire()).sum();
    }
}
