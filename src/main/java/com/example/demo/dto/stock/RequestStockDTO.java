package com.example.demo.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestStockDTO {
    @NotBlank(message="le numero de lot est obligatoire")
    private String numeroLot;

    @NotNull(message="la date d'entrée est obligatoire")
    private LocalDate dateEntree;

    @NotNull(message="la quantité est obligatoire")
    @Min(value = 0, message = "la quantité doit être positive ou nulle")
    private Integer quantite;


    @NotNull(message="le prix d'achat est obligatoire")
    @Positive(message = "le prix doit être positif")
    private Double prixAchatUnitaire;

    @NotNull(message = "le id de la commande est obligatoire")
    private Long commandeId;

    @NotNull(message = "Le id du produit est obligatoire")
    private Long produitId;



}
