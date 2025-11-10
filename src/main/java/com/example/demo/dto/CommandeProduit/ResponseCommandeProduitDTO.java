package com.example.demo.dto.CommandeProduit;

import com.example.demo.entity.Commande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommandeProduitDTO {
    private Long id;
    private Long produitId;
    private String produitNom;
    private Integer quantite;
    private Double prixUnitaire;
}
