package com.example.demo.mapper;

import com.example.demo.dto.CommandeProduit.ResponseCommandeProduitDTO;
import com.example.demo.entity.CommandeProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface CommandeProduitMapper {

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "produit.nom", target = "produitNom")
    ResponseCommandeProduitDTO toDto(CommandeProduit commandeProduit);

    CommandeProduit toEntity(ResponseCommandeProduitDTO dto);
}
