package com.example.demo.service;

import com.example.demo.dto.mouvemantStock.ResponseMouvementStockDTO;
import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.enums.TypeMouvement;

import java.util.List;

public interface MouvementStockService {
    List<ResponseMouvementStockDTO> getMouvementsStock(TypeMouvement type);
    List<ResponseMouvementStockDTO>getMouvementsStockByProduitId(Long produitId);
}
