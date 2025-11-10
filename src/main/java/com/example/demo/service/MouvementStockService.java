package com.example.demo.service;

import com.example.demo.dto.mouvemantStock.ResponseMouvementStockDTO;
import com.example.demo.entity.MouvementStock;

import java.util.List;

public interface MouvementStockService {
    List<ResponseMouvementStockDTO> getMouvementsStock();
    List<ResponseMouvementStockDTO>getMouvementsStockByProduitId(Long produitId);
}
