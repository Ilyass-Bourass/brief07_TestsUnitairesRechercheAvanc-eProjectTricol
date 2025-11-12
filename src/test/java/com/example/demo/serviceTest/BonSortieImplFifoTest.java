package com.example.demo.serviceTest;

import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.*;
import com.example.demo.entity.enums.StatutBonSortie;
import com.example.demo.entity.enums.TypeMouvement;
import com.example.demo.mapper.StockVersMouvementStockMapper;
import com.example.demo.repository.BonSortieRepository;
import com.example.demo.repository.MouvementStockRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.impl.BonSortieImpl;
import com.example.demo.service.impl.StockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BonSortieImplFifoTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockServiceImpl stockServiceImpl;

    @Mock
    private BonSortieRepository bonSortieRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private StockVersMouvementStockMapper stockVersMouvementStockMapper;

    @Mock
    private MouvementStockRepository mouvementStockRepository;

    @InjectMocks
    private BonSortieImpl bonSortieImpl;

    /*
    @Test
    void scenarioUn_sortiePartielle_sur_un_seul_lot() {
        Produit produit = new Produit();
        produit.setId(10L);
        produit.setStockActuel(100);

        Stock lot = new Stock();
        lot.setId(1L);
        lot.setQuantite(100);
        lot.setProduit(produit);

        ResponseStockDTO responseStockDTO = new ResponseStockDTO();
        responseStockDTO.setId(1L);
        responseStockDTO.setProduitId(10L);


        when(stockRepository.findById(1L)).thenReturn(Optional.of(lot));
        when(produitRepository.findById(10L)).thenReturn(Optional.of(produit));
        when(stockRepository.save(any(Stock.class))).thenReturn(lot);



        MouvementStock mouvementStock = new MouvementStock();
        when(stockVersMouvementStockMapper.stockVersMouvementStock(any(Stock.class)))
                .thenReturn(mouvementStock);

        bonSortieImpl.appliquerFifo(List.of(responseStockDTO), 30);

        assertEquals(70, lot.getQuantite(), "Le lot doit passer de 100 à 70");
        assertEquals(70, produit.getStockActuel(), "Le stock actuel du produit doit passer de 100 à 70");

        assertEquals(TypeMouvement.SORTIE, mouvementStock.getTypeMouvement());

        verify(stockRepository, times(1)).save(lot);
        verify(produitRepository, times(1)).save(produit);
        verify(mouvementStockRepository, times(1)).save(any(MouvementStock.class));
    }
     */

    @Test
    void scenarioUn_sortiePartielle_sur_un_seul_lot() {

        BonSortie bonSortie = new BonSortie();
        bonSortie.setId(1L);
        bonSortie.setStatutBonSortie(StatutBonSortie.BROULLION);

        Produit produit = new Produit();
        produit.setId(10L);
        produit.setStockActuel(100);

        DetailsBonSortie detail = new DetailsBonSortie();
        detail.setProduit(produit);
        detail.setQuantite(30);

        bonSortie.setDetailsBonSortie(List.of(detail));

        Stock lot = new Stock();
        lot.setId(1L);
        lot.setQuantite(100);
        lot.setProduit(produit);

        ResponseStockDTO responseStockDTO = new ResponseStockDTO();
        responseStockDTO.setId(1L);
        responseStockDTO.setProduitId(10L);

        when(bonSortieRepository.findById(1L)).thenReturn(Optional.of(bonSortie));
        when(produitRepository.findById(10L)).thenReturn(Optional.of(produit));
        when(stockRepository.findById(1L)).thenReturn(Optional.of(lot));
        when(stockRepository.save(any(Stock.class))).thenReturn(lot);
        when(stockServiceImpl.getStocksByProduit(10L)).thenReturn(List.of(responseStockDTO));

        MouvementStock mouvementStock = new MouvementStock();
        when(stockVersMouvementStockMapper.stockVersMouvementStock(any(Stock.class)))
                .thenReturn(mouvementStock);

        String result = bonSortieImpl.ValiderBonSortie(1L);

        assertEquals(70, lot.getQuantite(), "Le lot doit passer de 100 à 70");
        assertEquals(70, produit.getStockActuel(), "Le stock actuel doit passer de 100 à 70");
        assertEquals(StatutBonSortie.VALIDE, bonSortie.getStatutBonSortie(), "Le statut doit être VALIDE");
        assertEquals(TypeMouvement.SORTIE, mouvementStock.getTypeMouvement());

        assertTrue(result.contains("effuctué avec succée"));

        verify(stockRepository, times(1)).save(lot);
        verify(produitRepository, times(1)).save(produit);
        verify(bonSortieRepository, times(1)).save(bonSortie);
    }

}

