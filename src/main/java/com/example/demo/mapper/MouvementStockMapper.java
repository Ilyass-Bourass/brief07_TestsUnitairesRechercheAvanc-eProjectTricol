package com.example.demo.mapper;

import com.example.demo.dto.mouvemantStock.ResponseMouvementStockDTO;
import com.example.demo.entity.MouvementStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {
    MouvementStock toEntitie(ResponseMouvementStockDTO responseMouvementStockDTO);
    @Mapping(target = "produitId" ,source = "stock.produit.id")
    @Mapping(target = "nomStock" ,source = "stock.numeroLot")
    @Mapping(target = "nomProduit" ,source="stock.produit.nom")
    @Mapping(target = "stockActuel",source="stock.produit.stockActuel")
    @Mapping(target = "commandeId" ,source="stock.commande.id")
    @Mapping(target ="montantTotalCommande" ,source="stock.commande.montantTotal" )
    ResponseMouvementStockDTO toDto(MouvementStock mouvementStock);
}
