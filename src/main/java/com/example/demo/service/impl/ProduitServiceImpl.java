package com.example.demo.service.impl;

import com.example.demo.dto.produit.RequestProduitDTO;
import com.example.demo.dto.produit.ResponseProduitDTO;
import com.example.demo.entity.Produit;
import com.example.demo.mapper.ProduitMapper;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.service.ProduitService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    public ProduitServiceImpl(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    @Override
    public ResponseProduitDTO createProduit(RequestProduitDTO dto) {
        Produit produit = produitMapper.toEntity(dto);
        Produit saved = produitRepository.save(produit);
        return produitMapper.toResponseDTO(saved);
    }

    @Override
    public ResponseProduitDTO updateProduit(Long id, RequestProduitDTO dto) {
        Produit existing = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        produitMapper.updateEntityFromDTO(dto, existing);
        Produit updated = produitRepository.save(existing);
        return produitMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public ResponseProduitDTO getProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return produitMapper.toResponseDTO(produit);
    }

    @Override
    public List<ResponseProduitDTO> getAllProduits() {
        List<Produit> produits = produitRepository.findAll();
        return produitMapper.toResponseList(produits);
    }
}
