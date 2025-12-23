package com.example.demo.repository;

import com.example.demo.entity.RoleApp;
import com.example.demo.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleAppRepository extends JpaRepository<RoleApp,Long> {
    Optional<RoleApp> findByName(RoleName roleUtilisateur);
}
