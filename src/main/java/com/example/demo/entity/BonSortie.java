package com.example.demo.entity;

import com.example.demo.entity.enums.StatutBonSortie;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Table(name="bon_sortie")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonSortie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="le numero de bon de sortie est obligatoire")
    @Column(name="numero_bon", nullable=false)
    private String numeroBonSortie;

    @NotNull(message="la date de bon de sortie est obligatoire")
    @Column(name="date_sortie", nullable=false)
    private LocalDate dateBonSortie;

    @NotNull(message="atelier est obligatoire")
    @Column(name="atelier", nullable=false)
    private String atelier;

    @Column(name="motif", length=50)
    private String motif;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "le statut de bon de sortie est obligatoire")
    @Column(name="statut", nullable=false)
    private StatutBonSortie statutBonSortie;

    @OneToMany(mappedBy ="bonSortie",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetailsBonSortie> detailsBonSortie;

}
