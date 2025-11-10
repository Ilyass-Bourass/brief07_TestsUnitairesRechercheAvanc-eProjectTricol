package com.example.demo.dto.BonSortie;

import com.example.demo.dto.DetailsBonSortie.ResponseDetailsBonSortieDTO;
import com.example.demo.entity.enums.StatutBonSortie;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseBonSortieDTO {
    private Long id;
    private String numeroBonSortie;
    private LocalDate dateBonSortie;
    private String atelier;
    private String motif;
    private StatutBonSortie statutBonSortie;
    private List<ResponseDetailsBonSortieDTO> detailsBonSortie;
}
