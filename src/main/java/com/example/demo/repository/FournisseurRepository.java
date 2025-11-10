package com.example.demo.repository;

import com.example.demo.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur,Long> {
    boolean existsFournisseurByEmail(String email);
    boolean existsFournisseurByIce(String nom);
}
