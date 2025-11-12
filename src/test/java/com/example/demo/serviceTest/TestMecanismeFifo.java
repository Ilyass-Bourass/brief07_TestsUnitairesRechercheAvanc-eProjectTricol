package com.example.demo.serviceTest;

import com.example.demo.dto.BonSortie.ResponseBonSortieDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.*;
import com.example.demo.entity.enums.StatutBonSortie;
import com.example.demo.entity.enums.TypeMouvement;
import com.example.demo.mapper.StockVersMouvementStockMapper;
import com.example.demo.repository.BonSortieRepository;
import com.example.demo.repository.MouvementStockRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.BonSortieService;
import com.example.demo.service.impl.BonSortieImpl;
import com.example.demo.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestMecanismeFifo {

    @Mock
    private BonSortieRepository bonSortieRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockVersMouvementStockMapper  stockVersMouvementStockMapper;

    @Mock
    private StockServiceImpl stockService;


    @Mock
    private MouvementStockRepository  mouvementStockRepository;

    @InjectMocks
    private BonSortieImpl bonSortieService;


    private Produit produit;
    private DetailsBonSortie detailsBonSortie;
    private BonSortie bonSortie;

    @BeforeEach
    void setUp() {
        produit=Produit.builder().id(1L).stockActuel(100).build();
        detailsBonSortie=DetailsBonSortie.builder().id(1L).quantite(30).produit(produit).build();
        bonSortie=BonSortie.builder().id(1L).statutBonSortie(StatutBonSortie.BROULLION).detailsBonSortie(List.of(detailsBonSortie)).build();
    }

     @Test
     void senario_un_consomation_partielle_un_seul_lot(){

         Stock lot =Stock.builder().id(1L).quantite(100).produit(produit).build();
         ResponseStockDTO responseStockDTO =ResponseStockDTO.builder().id(1L).produitId(1L).build();

         when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
         when(stockRepository.findById(1L)).thenReturn(Optional.of(lot));
         when(stockRepository.save(any(Stock.class))).thenReturn(lot);
         when(bonSortieRepository.findById(1L)).thenReturn(Optional.of(bonSortie));
         when(stockService.getStocksByProduit(1L)).thenReturn(List.of(responseStockDTO));

         MouvementStock mouvementStock = new MouvementStock();
         when(stockVersMouvementStockMapper.stockVersMouvementStock(any(Stock.class)))
                 .thenReturn(mouvementStock);
         String result = bonSortieService.ValiderBonSortie(1L);

         assertEquals(70,lot.getQuantite(),"le lot doit passé de 100 à 70");
         assertEquals(70,produit.getStockActuel(),"le stock de produit doit passé de 100 à 70");
         assertEquals(StatutBonSortie.VALIDE,bonSortie.getStatutBonSortie(),"le statut doit être valide");
         assertEquals(TypeMouvement.SORTIE,mouvementStock.getTypeMouvement(),"le type de mouvement quand on fait bon de sortie est sortie");

         assertTrue(result.contains("effuctué avec succée"));

         verify(stockRepository, times(1)).save(lot);
         verify(produitRepository, times(1)).save(produit);
         verify(bonSortieRepository, times(1)).save(bonSortie);

     }
}
