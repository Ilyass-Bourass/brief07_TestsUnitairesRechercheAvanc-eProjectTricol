package com.example.demo.dto.commande;

import com.example.demo.dto.CommandeProduit.RequestCommandeProduitDTO;
import com.example.demo.dto.CommandeProduit.ResponseCommandeProduitDTO;
import com.example.demo.entity.enums.StatutCommande;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCommandeDTO {

    @NotNull(message = "la date de commande est obligatoire")
    private LocalDate dateCommande;

    private Double montantTotal;

    @NotNull(message = "le statut de la commande est obligatoire")
    private StatutCommande statutCommande;

    @NotNull(message = "Le fournisseur est obligatoire")
    private Long fournisseurId;

    @NotNull(message = "La liste des produits est obligatoire")
    private List<RequestCommandeProduitDTO> produits;

}
