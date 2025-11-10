package com.example.demo.mapper;

import com.example.demo.dto.fournisseur.RequestFournisseurDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;
import com.example.demo.entity.Fournisseur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface FournisseurMapper {
    ResponseFournisseurDTO toResponseDTO(Fournisseur fournisseur);
    Fournisseur toEntity(RequestFournisseurDTO requestDTO);
    void updateEntityFromDTO(RequestFournisseurDTO requestDTO, @MappingTarget Fournisseur fournisseur);
}
