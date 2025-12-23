package com.example.demo.service;

import com.example.demo.entity.enums.RoleName;

public interface ManagementUtilisateursService {
    String assignerRoleUtilisateur(Long idUtilisateur, String role);
}
