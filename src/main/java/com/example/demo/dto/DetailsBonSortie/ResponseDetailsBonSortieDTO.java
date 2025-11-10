package com.example.demo.dto.DetailsBonSortie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDetailsBonSortieDTO {
    private Long id;
    private Long bonSortieId;
    private Long produitId;
    private Integer quantiteDemander;
}

