package com.example.demo.mapper;


import com.example.demo.dto.stock.RequestStockDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(target = "commandeId" ,source="stock.commande.id")
    @Mapping(target = "montantTotalCommande" ,source="stock.commande.montantTotal")
    @Mapping(target = "produitId" ,source = "stock.produit.id")
    @Mapping(target = "nomProduit",source = "stock.produit.nom")
    @Mapping(target = "stockActuel" ,source="stock.produit.stockActuel")
    ResponseStockDTO toResponseStockDTO(Stock stock);

    Stock toStock(RequestStockDTO requestStockDTO);
    void updateStock(RequestStockDTO stockDTO, @MappingTarget Stock stock);
}
