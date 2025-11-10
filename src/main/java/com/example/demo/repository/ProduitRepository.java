package com.example.demo.repository;

import com.example.demo.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Produit findByReference(String reference);
    boolean existsProduitById(Long id);
}
