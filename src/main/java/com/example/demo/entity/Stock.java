package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="numero_lot")
    @NotBlank(message="le numero de lot est obligatoire")
    private String numeroLot;

    @Column(name="date_entree")
    @NotNull(message="la date d'entrée est obligatoire")
    private LocalDateTime dateEntree;

    @NotNull(message="la quantité est obligatoire")
    @Min(value = 0, message = "la quantité doit être positive ou nulle")
    private Integer quantite;

    @NotNull(message="le prix d'achat est obligatoire")
    @Positive(message = "le prix doit être positif")
    @Column(name="prix_achat_unitaire")
    private Double prixAchatUnitaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commande_id")
    @NotNull(message="le id de la commande est obligatoire")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="produit_id", nullable=false)
    @NotNull(message = "le id du produit est obligatoire")
    private Produit produit;
}
