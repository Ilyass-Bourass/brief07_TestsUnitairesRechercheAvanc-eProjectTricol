package com.example.demo.dto.stock;

import com.example.demo.entity.enums.StatutCommande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseStockDTO {
    private Long id;
    private String numeroLot;
    private LocalDateTime dateEntree;
    private Integer quantite;
    private Double prixAchatUnitaire;
    private Long commandeId;
    private Double montantTotalCommande;
    private Long produitId;
    private String nomProduit;
    private Integer stockActuel;
}
