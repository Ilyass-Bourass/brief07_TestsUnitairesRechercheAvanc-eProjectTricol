package com.example.demo.mapper;

import com.example.demo.dto.produit.RequestProduitDTO;
import com.example.demo.dto.produit.ResponseProduitDTO;
import com.example.demo.entity.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    Produit toEntity(RequestProduitDTO dto);

    ResponseProduitDTO toResponseDTO(Produit entity);

    List<ResponseProduitDTO> toResponseList(List<Produit> entities);

    void updateEntityFromDTO(RequestProduitDTO dto, @MappingTarget Produit entity);
}
