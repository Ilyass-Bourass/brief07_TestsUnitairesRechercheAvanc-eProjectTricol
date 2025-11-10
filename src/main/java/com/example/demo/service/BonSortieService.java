package com.example.demo.service;

import com.example.demo.dto.BonSortie.RequestBonSortieDTO;
import com.example.demo.dto.BonSortie.ResponseBonSortieDTO;
import com.example.demo.dto.stock.ResponseStockDTO;

import java.util.List;

public interface BonSortieService {
    ResponseBonSortieDTO createBonSotie(RequestBonSortieDTO requestBonSortieDTO);
    List<ResponseBonSortieDTO> findAllBonSortie();
    ResponseBonSortieDTO findBonSortieById(Long id);
    void deleteBonSotie(Long id);
    ResponseBonSortieDTO updateBonSortie(Long id, RequestBonSortieDTO requestBonSortieDTO);
    ResponseBonSortieDTO annulerBonSortie(Long id);
    List<ResponseBonSortieDTO> findAllBonSortieByAtelier(String atelier);
    boolean verfierBonsortieEtStockActuelProduit(Long idProduit,Integer quantiteBonSortie);
    String ValiderBonSortie(Long idBonSortie);
    void appliquerFifo(List<ResponseStockDTO> responseStockByProduitDTO, Integer quantiteBonSortie);
}
