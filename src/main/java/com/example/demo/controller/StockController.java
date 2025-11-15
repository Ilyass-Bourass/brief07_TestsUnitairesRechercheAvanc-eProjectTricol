package com.example.demo.controller;
import com.example.demo.dto.mouvemantStock.ResponseMouvementStockDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.Stock;
import com.example.demo.entity.enums.TypeMouvement;
import com.example.demo.service.MouvementStockService;
import com.example.demo.service.impl.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/valorisation")
    public ResponseEntity<Double> getAllStocks(){
        return ResponseEntity.ok(stockService.valorosiationStocksTotal());
    }

    @GetMapping("/mouvements")
    public ResponseEntity<List<ResponseMouvementStockDTO>>

    sercheMouvementStock(
                @RequestParam (required = false) TypeMouvement type,
                @RequestParam (required = false)LocalDateTime start,
                @RequestParam (required = false)LocalDateTime end,
                @RequestParam (required = false) Long idProduit,
                @RequestParam (required = false) String numeroLot
        )
    {
        List<ResponseMouvementStockDTO> mouvementStocks=mouvementStockService.getMouvementsStock(type,start,end,idProduit,numeroLot);
        return new ResponseEntity<>(mouvementStocks, HttpStatus.OK);
    }

    @GetMapping("/mouvements/produit/{id}")
    public ResponseEntity<List<ResponseMouvementStockDTO>> getAllMouvementsByProduit(@PathVariable Long id){
        List<ResponseMouvementStockDTO> responseMouvementStockDTOS=mouvementStockService.getMouvementsStockByProduitId(id);
        return new ResponseEntity<>(responseMouvementStockDTOS, HttpStatus.OK);
    }


}
