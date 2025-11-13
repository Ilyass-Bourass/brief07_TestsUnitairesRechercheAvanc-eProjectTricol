package com.example.demo.serviceTest;


import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.*;
import com.example.demo.entity.enums.StatutCommande;
import com.example.demo.mapper.*;
import com.example.demo.repository.*;
import com.example.demo.service.impl.CommandeServiceImpl;
import com.example.demo.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class testCommandeAvecLotStock {
    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private CommandeMapper  commandeMapper;

    @Mock
    private FournisseurMapper fournisseurMapper;

    @Mock
    private StockVersMouvementStockMapper stockVersMouvementStockMapper;

    @Mock
    private DetailsCommandeVersStockMapper  detailsCommandeVersStockMapper;

    @Mock
    private StockMapper stockMapper;

    @Mock
    private MouvementStockRepository  mouvementStockRepository;


    @InjectMocks
    private CommandeServiceImpl commandeService;

    @InjectMocks
    private StockServiceImpl stockService;

    private Fournisseur fournisseur;
    private Produit produit;
    private Commande commande;
    private CommandeProduit commandeProduit;

    @BeforeEach
    void setup(){
        fournisseur =Fournisseur.builder().id(1l).build();
        produit =Produit.builder().id(1l).stockActuel(100).build();
        commandeProduit=CommandeProduit.builder().id(1l).produit(produit).quantite(50).prixUnitaire(300.0).build();
        commande =Commande.builder().id(1L).fournisseur(fournisseur).commandeProduits(List.of(commandeProduit)).statutCommande(StatutCommande.EN_ATTENTE).build();

    }

    @Test
    void CreerUnLotDeStockApresValidationCommandeFournisseur() {
        Stock nouveauStock = Stock.builder()
                .numeroLot("LOT-" + UUID.randomUUID())
                .produit(produit)
                .quantite(50)
                .prixAchatUnitaire(300.0)
                .dateEntree(LocalDateTime.now())
                .build();

        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        when(detailsCommandeVersStockMapper.detailsCommandeToStock(commandeProduit, commande, produit)).thenReturn(nouveauStock);

        commandeService.validerCommande(1L);

        ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockRepository, times(1)).save(stockCaptor.capture());
        Stock savedStock = stockCaptor.getValue();

        assertNotNull(savedStock, "Le stock ne doit pas être null");
        assertTrue(savedStock.getNumeroLot().startsWith("LOT-"), "Le numéro de lot doit commencer par 'LOT-'");
        assertNotNull(savedStock.getDateEntree(), "La date d'entrée doit être définie automatiquement");
        assertEquals(50, savedStock.getQuantite(), "La quantité doit être correcte");
        assertEquals(300.0, savedStock.getPrixAchatUnitaire(), "Le prix d'achat unitaire doit être correct");

        assertEquals(StatutCommande.VALIDEE, commande.getStatutCommande(), "Le statut de la commande doit être VALIDEE");
    }

    @Test
    void testValorisationStockTotal_AvecUnSeulLot() {

        Stock stock1 = Stock.builder()
                .id(1L)
                .numeroLot("LOT-001")
                .produit(produit)
                .quantite(50)
                .prixAchatUnitaire(100.0)
                .dateEntree(LocalDateTime.now())
                .build();

        ResponseStockDTO responseStockDTO1 = ResponseStockDTO.builder()
                .id(1L)
                .numeroLot("LOT-001")
                .produitId(1L)
                .quantite(50)
                .prixAchatUnitaire(100.0)
                .dateEntree(LocalDateTime.now())
                .build();

        when(stockRepository.findAll()).thenReturn(List.of(stock1));
        when(stockMapper.toResponseStockDTO(stock1)).thenReturn(responseStockDTO1);

        double valorisationTotal = stockService.valorosiationStocksTotal();

        assertEquals(5000.0, valorisationTotal, "La valorisation doit être 50 * 100.0 = 5000.0");
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void testValorisationStockTotal_AvecPlusieursLotsAPrixDifferents() {
        Stock stock1 = Stock.builder()
                .id(1L)
                .numeroLot("LOT-001")
                .produit(produit)
                .quantite(50)
                .prixAchatUnitaire(100.0)
                .dateEntree(LocalDateTime.now().minusDays(5))
                .build();

        Stock stock2 = Stock.builder()
                .id(2L)
                .numeroLot("LOT-002")
                .produit(produit)
                .quantite(30)
                .prixAchatUnitaire(150.0)
                .dateEntree(LocalDateTime.now().minusDays(3))
                .build();


        ResponseStockDTO responseStockDTO1 = ResponseStockDTO.builder()
                .id(1L)
                .numeroLot("LOT-001")
                .produitId(1L)
                .quantite(50)
                .prixAchatUnitaire(100.0)
                .dateEntree(LocalDateTime.now().minusDays(5))
                .build();

        ResponseStockDTO responseStockDTO2 = ResponseStockDTO.builder()
                .id(2L)
                .numeroLot("LOT-002")
                .produitId(1L)
                .quantite(30)
                .prixAchatUnitaire(150.0)
                .dateEntree(LocalDateTime.now().minusDays(3))
                .build();


        when(stockRepository.findAll()).thenReturn(List.of(stock1, stock2));
        when(stockMapper.toResponseStockDTO(stock1)).thenReturn(responseStockDTO1);
        when(stockMapper.toResponseStockDTO(stock2)).thenReturn(responseStockDTO2);

        double valorisationTotal = stockService.valorosiationStocksTotal();

        assertEquals(9500.0, valorisationTotal, "La valorisation totale doit être la somme de tous les lots");
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void testValorisationStockTotal_AvecStockVide() {
        when(stockRepository.findAll()).thenReturn(List.of());
        double valorisationTotal = stockService.valorosiationStocksTotal();
        assertEquals(0.0, valorisationTotal, "La valorisation doit être 0.0 quand il n'y a pas de stock");
        verify(stockRepository, times(1)).findAll();
    }



}
