package com.example.demo.dto.produit;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RequestProduitDTO {

    @NotBlank(message = "La référence est obligatoire")
    private String reference;

    @NotBlank(message = "Le nom du produit est obligatoire")
    private String nom;

    private String description;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
    private BigDecimal prixUnitaire;

    private String categorie;

    @NotNull(message = "Le stock actuel est obligatoire")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private Integer stockActuel;

    @NotNull(message = "Le stock minimum est obligatoire")
    @Min(value = 0, message = "Le stock minimum ne peut pas être négatif")
    private Integer stockMinimum;

    private String uniteMesure;
}
