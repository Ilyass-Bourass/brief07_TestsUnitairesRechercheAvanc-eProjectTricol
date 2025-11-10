package com.example.demo.service.impl;

import com.example.demo.dto.mouvemantStock.ResponseMouvementStockDTO;
import com.example.demo.entity.MouvementStock;
import com.example.demo.mapper.MouvementStockMapper;
import com.example.demo.repository.MouvementStockRepository;
import com.example.demo.service.MouvementStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MouvementStockimpl implements MouvementStockService {
    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;

    @Override
    public List<ResponseMouvementStockDTO> getMouvementsStock() {
        return mouvementStockRepository.findAll().stream().map(mouvementStockMapper::toDto).toList();

    }

    @Override
    public List<ResponseMouvementStockDTO> getMouvementsStockByProduitId(Long produitId) {
        return getMouvementsStock().stream().filter(Rms->Rms.getProduitId().equals(produitId)).toList();
    }
}
