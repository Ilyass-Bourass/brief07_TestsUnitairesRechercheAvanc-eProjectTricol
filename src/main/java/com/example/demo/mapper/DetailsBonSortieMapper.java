package com.example.demo.mapper;


import com.example.demo.dto.DetailsBonSortie.RequestDetailsBonSortieDTO;
import com.example.demo.dto.DetailsBonSortie.ResponseDetailsBonSortieDTO;
import com.example.demo.entity.DetailsBonSortie;
import com.example.demo.entity.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetailsBonSortieMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonSortie", ignore = true)
    @Mapping(target = "produit", expression = "java(mapProduit(requestDetailsBonSortieDTO.getProduitId()))")
    DetailsBonSortie toEntity(RequestDetailsBonSortieDTO requestDetailsBonSortieDTO);

    @Mapping(target = "bonSortieId", source = "bonSortie.id")
    @Mapping(target = "produitId", source = "produit.id")
    @Mapping(target = "quantiteDemander", source = "quantite")
    ResponseDetailsBonSortieDTO toDTO(DetailsBonSortie detailsBonSortie);

    default Produit mapProduit(Long produitId) {
        if (produitId == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId(produitId);
        return produit;
    }
}
