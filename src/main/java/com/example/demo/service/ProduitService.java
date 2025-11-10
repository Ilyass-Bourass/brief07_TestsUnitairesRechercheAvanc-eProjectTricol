package com.example.demo.service;

import com.example.demo.dto.produit.RequestProduitDTO;
import com.example.demo.dto.produit.ResponseProduitDTO;
import java.util.List;

public interface ProduitService {

    ResponseProduitDTO createProduit(RequestProduitDTO dto);

    ResponseProduitDTO updateProduit(Long id, RequestProduitDTO dto);

    void deleteProduit(Long id);

    ResponseProduitDTO getProduitById(Long id);

    List<ResponseProduitDTO> getAllProduits();
}
