package com.example.demo.dto.BonSortie;

import com.example.demo.dto.DetailsBonSortie.RequestDetailsBonSortieDTO;
import com.example.demo.entity.enums.StatutBonSortie;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBonSortieDTO {

    private String numeroBonSortie;

    private LocalDate dateBonSortie;

    @NotNull(message="atelier est obligatoire")
    private String atelier;

    private String motif;

    private StatutBonSortie statutBonSortie;

    private List<RequestDetailsBonSortieDTO> detailsBonSortie;
}
