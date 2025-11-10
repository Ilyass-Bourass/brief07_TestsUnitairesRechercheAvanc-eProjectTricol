package com.example.demo.dto.commande;

import com.example.demo.dto.CommandeProduit.ResponseCommandeProduitDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;
import com.example.demo.entity.enums.StatutCommande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommandeDTO {
    private Long id;
    private LocalDate dateCommande;
    private Double montantTotal;
    private StatutCommande statutCommande;
    private Long fournisseurId;
    private String fournisseurNom;
    private List<ResponseCommandeProduitDTO> produits;
}
