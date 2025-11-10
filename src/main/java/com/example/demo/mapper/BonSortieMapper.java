package com.example.demo.mapper;

import com.example.demo.dto.BonSortie.RequestBonSortieDTO;
import com.example.demo.dto.BonSortie.ResponseBonSortieDTO;
import com.example.demo.entity.BonSortie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {DetailsBonSortieMapper.class})
public interface BonSortieMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroBonSortie", expression = "java(creerNumeroBonSortie())")
    @Mapping(target = "detailsBonSortie", source = "detailsBonSortie")
    @Mapping(target = "dateBonSortie", expression = "java(java.time.LocalDate.now())")
    BonSortie toEntity(RequestBonSortieDTO requestBonSortieDTO);

    @Mapping(target = "detailsBonSortie", source = "detailsBonSortie")
    ResponseBonSortieDTO toDTO(BonSortie bonSortie);

    default String creerNumeroBonSortie(){
        return "NBS:"+ UUID.randomUUID().toString().substring(0, 8);
    }
}
