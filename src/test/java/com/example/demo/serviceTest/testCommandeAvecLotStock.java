package com.example.demo.serviceTest;


import com.example.demo.entity.*;
import com.example.demo.entity.enums.StatutCommande;
import com.example.demo.mapper.CommandeMapper;
import com.example.demo.mapper.DetailsCommandeVersStockMapper;
import com.example.demo.mapper.FournisseurMapper;
import com.example.demo.mapper.StockVersMouvementStockMapper;
import com.example.demo.repository.*;
import com.example.demo.service.impl.CommandeServiceImpl;
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
    private MouvementStockRepository  mouvementStockRepository;


    @InjectMocks
    private CommandeServiceImpl commandeService;

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

}

