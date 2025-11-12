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
import jdk.jfr.Name;
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
         ResponseStockDTO responseStockByProduitDTO =ResponseStockDTO.builder().id(1L).produitId(1L).build();

         when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
         when(stockRepository.findById(1L)).thenReturn(Optional.of(lot));
         when(stockRepository.save(any(Stock.class))).thenReturn(lot);
         when(bonSortieRepository.findById(1L)).thenReturn(Optional.of(bonSortie));
         when(stockService.getStocksByProduit(1L)).thenReturn(List.of(responseStockByProduitDTO));

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

     @Test
     void senarion_deux_consomation_plusieurs_lot(){

        Stock lot01=Stock.builder().id(1L).quantite(10).produit(produit).build();
        Stock lot02=Stock.builder().id(2L).quantite(40).produit(produit).build();

        ResponseStockDTO responseStockByProduitDTO01 =ResponseStockDTO.builder().id(1L).produitId(1L).build();
        ResponseStockDTO responseStockByProduitDTO02 =ResponseStockDTO.builder().id(2L).produitId(1L).build();

         when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
         when(stockRepository.findById(1L)).thenReturn(Optional.of(lot01));
         when(stockRepository.findById(2L)).thenReturn(Optional.of(lot02));
         when(stockRepository.save(lot01)).thenReturn(lot01);
         when(stockRepository.save(lot02)).thenReturn(lot02);
         when(bonSortieRepository.findById(1L)).thenReturn(Optional.of(bonSortie));
         when(stockService.getStocksByProduit(1L)).thenReturn(List.of(responseStockByProduitDTO01,responseStockByProduitDTO02));

         MouvementStock mouvementStock01 = new MouvementStock();
         MouvementStock mouvementStock02 = new MouvementStock();
         when(stockVersMouvementStockMapper.stockVersMouvementStock(lot01))
                 .thenReturn(mouvementStock01);
         when(stockVersMouvementStockMapper.stockVersMouvementStock(lot02))
                 .thenReturn(mouvementStock02);

         String result= bonSortieService.ValiderBonSortie(1L);
         assertEquals(0,lot01.getQuantite(),"le stock 01 vide");
         assertEquals(20,lot02.getQuantite(),"le stock 02 doit dépasser de 40 à 30");
         assertEquals(StatutBonSortie.VALIDE,bonSortie.getStatutBonSortie());
         assertEquals(TypeMouvement.SORTIE,mouvementStock01.getTypeMouvement());
         assertEquals(TypeMouvement.SORTIE,mouvementStock02.getTypeMouvement());
         assertEquals(70,produit.getStockActuel(),"le stock dépasser de 100 à 70");
         assertTrue(result.contains("effuctué avec succée"));

         verify(bonSortieRepository, times(1)).save(bonSortie);
         verify(stockRepository, times(2)).save(any(Stock.class));
         verify(mouvementStockRepository, times(2)).save(any(MouvementStock.class));
         verify(produitRepository, times(2)).save(any(Produit.class));
     }
    /*
     @Test
     void senario_03_Sortie_avec_stock_insuffisant(){
        Stock stock01=Stock.builder().id(1L).produit(produit).quantite(40).build();

     }

     */
}
