package com.example.demo.mapper;


import com.example.demo.dto.commande.ResponseCommandeDTO;
import com.example.demo.entity.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" ,  uses = CommandeProduitMapper.class)
public interface CommandeMapper {

    @Mapping(source = "fournisseur.id", target = "fournisseurId")
    @Mapping(source = "fournisseur.raisonSociale", target = "fournisseurNom")
    @Mapping(source = "commandeProduits", target = "produits")
    ResponseCommandeDTO toResponseDTO(Commande commandeProduit);

    Commande toEntity(ResponseCommandeDTO requestDTO);
}
