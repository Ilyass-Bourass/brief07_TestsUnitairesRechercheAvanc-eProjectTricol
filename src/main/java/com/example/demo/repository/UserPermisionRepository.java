package com.example.demo.repository;

import com.example.demo.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermisionRepository extends JpaRepository<UserPermission,Long> {
}
