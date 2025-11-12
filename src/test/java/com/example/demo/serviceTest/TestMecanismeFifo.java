package com.example.demo.serviceTest;

import com.example.demo.dto.BonSortie.ResponseBonSortieDTO;
import com.example.demo.entity.BonSortie;
import com.example.demo.entity.DetailsBonSortie;
import com.example.demo.entity.Produit;
import com.example.demo.entity.enums.StatutBonSortie;
import com.example.demo.mapper.StockVersMouvementStockMapper;
import com.example.demo.repository.BonSortieRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.BonSortieService;
import com.example.demo.service.impl.BonSortieImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

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

    @InjectMocks
    private BonSortieService bonSortieService;

     @Test
     void senario_un_consomation_partielle_un_seul_lot(){

         Produit p = new Produit();
         p.setId(1L);
         p.setStockActuel(100);

         DetailsBonSortie detailsBonSortie = new DetailsBonSortie();
         detailsBonSortie.setId(1L);
         detailsBonSortie.setProduit(p);


         BonSortie b = new BonSortie();
         b.setStatutBonSortie(StatutBonSortie.BROULLION);
         b.setDetailsBonSortie(List.of(detailsBonSortie));

         ResponseBonSortieDTO responseBonSortieDTO = new ResponseBonSortieDTO();
         responseBonSortieDTO.setId(1L);
         responseBonSortieDTO.setDetailsBonSortie();

         when(bonSortieRepository.save(b)).thenReturn(b);
         when(bonSortieRepository.findById(1L)).thenReturn(Optional.of(b));
         when(produitRepository.findById(1L)).thenReturn(Optional.of(p));

     }
}
