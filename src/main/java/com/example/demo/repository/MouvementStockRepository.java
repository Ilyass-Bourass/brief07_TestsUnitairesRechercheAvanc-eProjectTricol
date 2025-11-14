package com.example.demo.repository;

import com.example.demo.entity.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> , JpaSpecificationExecutor<MouvementStock> {
}
