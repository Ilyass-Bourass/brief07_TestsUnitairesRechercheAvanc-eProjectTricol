package com.example.demo.mapper;

import com.example.demo.entity.Commande;
import com.example.demo.entity.CommandeProduit;
import com.example.demo.entity.Produit;
import com.example.demo.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface DetailsCommandeVersStockMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroLot", expression = "java(generateNumeroLot(commande.getId(), produit.getId()))")
    @Mapping(target = "dateEntree", expression = "java(LocalDateTime.now())")
    @Mapping(target = "quantite", source = "commandeProduit.quantite")
    @Mapping(target = "prixAchatUnitaire", source = "commandeProduit.prixUnitaire")
    @Mapping(target = "produit", source = "produit")
    @Mapping(target = "commande", source = "commande")
    Stock detailsCommandeToStock(CommandeProduit commandeProduit, Commande commande, Produit produit);

    default String generateNumeroLot(Long commandeId, Long produitId) {
        return "LOT-CMD-" + commandeId + "-PROD-" + produitId;
    }
}
