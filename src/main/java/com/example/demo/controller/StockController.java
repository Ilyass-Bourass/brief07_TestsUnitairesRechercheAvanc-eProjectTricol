package com.example.demo.controller;
import com.example.demo.dto.mouvemantStock.ResponseMouvementStockDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.Stock;
import com.example.demo.service.MouvementStockService;
import com.example.demo.service.impl.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockServiceImpl stockService;
    private final MouvementStockService mouvementStockService;

    @GetMapping
    public ResponseEntity<List<ResponseStockDTO>> getStock(){
        List<ResponseStockDTO> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("produit/{id}")
    public ResponseEntity<List<ResponseStockDTO>> getStockByProduit(@PathVariable Long id){
        List<ResponseStockDTO> stoks=stockService.getStocksByProduit(id);
        return new ResponseEntity<>(stoks, HttpStatus.OK);
    }

    @GetMapping("/mouvements")
    public ResponseEntity<List<ResponseMouvementStockDTO>> getAllMouvements(){
        List<ResponseMouvementStockDTO> mouvementStocks=mouvementStockService.getMouvementsStock();
        return new ResponseEntity<>(mouvementStocks, HttpStatus.OK);
    }

    @GetMapping("/mouvements/produit/{id}")
    public ResponseEntity<List<ResponseMouvementStockDTO>> getAllMouvementsByProduit(@PathVariable Long id){
        List<ResponseMouvementStockDTO> responseMouvementStockDTOS=mouvementStockService.getMouvementsStockByProduitId(id);
        return new ResponseEntity<>(responseMouvementStockDTOS, HttpStatus.OK);
    }

}
