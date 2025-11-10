package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true)
    private String reference;

    @Column(length = 100, nullable = false)
    private String nom;

    @Column(length = 255)
    private String description;

    @Column(name = "prix_unitaire", precision = 10, scale = 2, nullable = false)
    private BigDecimal prixUnitaire;

    @Column(length = 100)
    private String categorie;

    @Column(name = "stock_actuel")
    private Integer stockActuel;

    @Column(name = "stock_minimum")
    private Integer stockMinimum;

    @Column(name = "unite_mesure", length = 20)
    private String uniteMesure;

    @OneToMany(mappedBy = "produit")
    private List<CommandeProduit> commandeProduits;

}
