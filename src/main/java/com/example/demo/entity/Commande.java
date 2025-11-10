package com.example.demo.entity;

import com.example.demo.entity.enums.StatutCommande;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="date_commande")
    @NotNull(message = "la date de commande est obligatoire")
    private LocalDate dateCommande;

    @Column(name="montant_total")
    private Double montantTotal;

    @NotNull(message="le statut de la commande est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name="statut")
    private StatutCommande statutCommande;

    @NotNull(message = "Le fournisseur est obligatoire")
    @ManyToOne
    @JoinColumn(name="fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeProduit> commandeProduits;
    /*
    @ManyToOne
    private Stock stock;
     */

}
