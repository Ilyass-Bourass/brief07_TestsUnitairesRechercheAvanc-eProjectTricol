package com.example.demo.entity;

import com.example.demo.entity.enums.TypeMouvement;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="mouvement_stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="stock_id")
    private Stock stock;

    @NotNull(message = "la quantite obligatoire")
    @Min(value = 0, message = "la quantité doit être positive ou nulle")
    private Integer quantite;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    @NotNull(message = "Le type de mouvement est obligatoire")
    private TypeMouvement typeMouvement;

    @Column(name="date_mouvement")
    @NotNull(message = "La date est obligatoire")
    private LocalDateTime dateMouvement;

}
