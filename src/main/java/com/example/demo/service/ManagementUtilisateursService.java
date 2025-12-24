package com.example.demo.service;

import com.example.demo.entity.enums.RoleName;

public interface ManagementUtilisateursService {
    String assignerRoleUtilisateur(Long idUtilisateur, String role);
    String assignerPermissionUtilisateur(Long idUtilisateur, String permission);
    String supprimerPermissionUtilisateur(Long idUtilisateur, String permission);
}
