package com.example.demo.dto.produit;


import lombok.Data;
import java.math.BigDecimal;

@Data

public class ResponseProduitDTO {

    private Long id;
    private String reference;
    private String nom;
    private String description;
    private BigDecimal prixUnitaire;
    private String categorie;
    private Integer stockActuel;
    private Integer stockMinimum;
    private String uniteMesure;
}
