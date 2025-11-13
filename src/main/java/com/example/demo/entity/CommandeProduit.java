package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="commande_produit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CommandeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="la quantité est obligatoire")
    @Min(value = 1, message = "la quantité doit être au moins 1")
    private Integer quantite;

    @NotNull(message = "le prix unitaire est obligatoire")
    @PositiveOrZero(message = "le prix unitaire doit être positif ou nul")
    @Column(name="prix_unitaire")
    private Double prixUnitaire;

    @ManyToOne
    @JoinColumn(name="commande_id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name="produit_id")
    private Produit produit;
}
