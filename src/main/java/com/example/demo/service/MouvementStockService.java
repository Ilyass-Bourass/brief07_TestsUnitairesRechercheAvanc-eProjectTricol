package com.example.demo.service;

import com.example.demo.dto.mouvemantStock.ResponseMouvementStockDTO;
import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.enums.TypeMouvement;

import java.time.LocalDateTime;
import java.util.List;

public interface MouvementStockService {
    public List<ResponseMouvementStockDTO> getMouvementsStock(TypeMouvement type, LocalDateTime star, LocalDateTime end,Long idProduit,String numeroLot);
        List<ResponseMouvementStockDTO>getMouvementsStockByProduitId(Long produitId);
}
