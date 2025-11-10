package com.example.demo.mapper;


import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.Stock;
import com.example.demo.entity.enums.TypeMouvement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring" ,imports = {LocalDateTime.class , TypeMouvement.class})
public interface StockVersMouvementStockMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "stock" ,source = "stock")
    @Mapping(target = "quantite" ,source = "stock.quantite")
    @Mapping(target = "typeMouvement" ,expression = "java(TypeMouvement.ENTREE)")
    @Mapping(target = "dateMouvement" ,expression = "java(LocalDateTime.now())")
    MouvementStock stockVersMouvementStock(Stock stock);
}
