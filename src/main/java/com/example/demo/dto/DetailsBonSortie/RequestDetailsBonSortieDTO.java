package com.example.demo.dto.DetailsBonSortie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailsBonSortieDTO {
    private Long produitId;
    private Integer quantite;
}
