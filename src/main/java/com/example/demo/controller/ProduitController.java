package com.example.demo.controller;

import com.example.demo.dto.produit.RequestProduitDTO;
import com.example.demo.dto.produit.ResponseProduitDTO;
import com.example.demo.service.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping
    public ResponseEntity<ResponseProduitDTO> create(@Valid @RequestBody RequestProduitDTO dto) {
        return ResponseEntity.ok(produitService.createProduit(dto));
    }

    @GetMapping
    public ResponseEntity<List<ResponseProduitDTO>> getAll() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProduitDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProduitDTO> update(@PathVariable Long id, @Valid @RequestBody RequestProduitDTO dto) {
        return ResponseEntity.ok(produitService.updateProduit(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
