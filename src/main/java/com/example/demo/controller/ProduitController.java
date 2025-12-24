package com.example.demo.controller;

import com.example.demo.dto.produit.RequestProduitDTO;
import com.example.demo.dto.produit.ResponseProduitDTO;
import com.example.demo.service.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @PostMapping
    public ResponseEntity<ResponseProduitDTO> create(@Valid @RequestBody RequestProduitDTO dto) {
        return ResponseEntity.ok(produitService.createProduit(dto));
    }

    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    @GetMapping
    public ResponseEntity<List<ResponseProduitDTO>> getAll() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProduitDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseProduitDTO> update(@PathVariable Long id, @Valid @RequestBody RequestProduitDTO dto) {
        return ResponseEntity.ok(produitService.updateProduit(id, dto));
    }

    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
