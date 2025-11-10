package com.example.demo.dto.mouvemantStock;


import com.example.demo.entity.Stock;
import com.example.demo.entity.enums.TypeMouvement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseMouvementStockDTO {
    private Long id;
    private String nomStock;
    private Long produitId;
    private String nomProduit;
    private Integer stockActuel;
    private Long commandeId;
    private Double montantTotalCommande;
    private Integer quantite;
    private TypeMouvement typeMouvement;
    private LocalDateTime dateMouvement;
}
