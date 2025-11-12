package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="bon_sortie_ligne")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DetailsBonSortie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bon_sortie_id",nullable = false)
    private BonSortie bonSortie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="produit_id")
    private Produit produit;

    private Integer quantite;

}
