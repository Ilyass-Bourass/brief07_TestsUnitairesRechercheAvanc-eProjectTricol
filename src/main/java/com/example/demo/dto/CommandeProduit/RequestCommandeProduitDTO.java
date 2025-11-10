package com.example.demo.dto.CommandeProduit;

import com.example.demo.entity.Commande;
import com.example.demo.entity.Produit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RequestCommandeProduitDTO {

    private Long id;

    @NotNull(message="la quantité est obligatoire")
    @Min(value = 1, message = "la quantité doit être au moins 1")
    private Integer quantite;

    @NotNull(message = "le prix unitaire est obligatoire")
    @PositiveOrZero(message = "le prix unitaire doit être positif ou nul")
    private Double prixUnitaire;

}
