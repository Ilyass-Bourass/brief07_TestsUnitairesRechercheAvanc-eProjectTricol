package com.example.demo.service.impl;

import com.example.demo.entity.Permission;
import com.example.demo.entity.RoleApp;
import com.example.demo.entity.UserApp;
import com.example.demo.entity.UserPermission;
import com.example.demo.entity.enums.RoleName;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleAppRepository;
import com.example.demo.repository.UserAppRepository;
import com.example.demo.repository.UserPermissionRepository;
import com.example.demo.service.ManagementUtilisateursService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor

public class ManegementUtilisateursServiceImpl implements ManagementUtilisateursService {

    private final UserAppRepository userAppRepository;
    private final RoleAppRepository roleAppRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    @Override
    public String assignerRoleUtilisateur(Long idUtilisateur, String roleUtilisateur) {

        UserApp userApp = userAppRepository.findById(idUtilisateur).orElseThrow(()-> new ResourceNotFoundException("Utilisateur non trouvé"));

        if(userApp.getRole() != null){
            throw new IllegalStateException("L'utilisateur a déjà un rôle assigné.");
        }

        List<String> validRoles = List.of("RESPONSABLE_ACHATS", "MAGASINIER", "CHEF_ATELIER");

        RoleName roleName;
        try {
            roleName = RoleName.valueOf(roleUtilisateur.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Rôle invalide. Rôles valides : RESPONSABLE_ACHATS, MAGASINIER, CHEF_ATELIER"
            );
        }

        RoleApp roleApp = roleAppRepository.findByName(roleName).orElseThrow(()-> new ResourceNotFoundException("Rôle non trouvé"));


        Set<Permission> permissions = roleApp.getDefaultPermissions();
        Set<UserPermission> defaultUserPermissions = permissions.stream().map(
                permission -> UserPermission.builder()
                        .user(userApp)
                        .permission(permission)
                        .modifiedBy("admin")
                        .modifiedAt(LocalDateTime.now())
                        .build()
        ).collect(toSet());

        userPermissionRepository.saveAll(defaultUserPermissions);

        userApp.setRole(roleApp);

        userApp.getUserPermissions().clear();
        userApp.getUserPermissions().addAll(defaultUserPermissions);
        userApp.setUpdatedAt(LocalDateTime.now());

        userAppRepository.save(userApp);


        return "Rôle " + roleUtilisateur + " assigné à l'utilisateur avec succès.";
    }

    @Override
    @Transactional
    public String assignerPermissionUtilisateur(Long idUtilisateur, String permission) {

        UserApp userApp = userAppRepository.findById(idUtilisateur).orElseThrow(()-> new ResourceNotFoundException("Utilisateur non trouvé"));
        Permission permissionOpt = permissionRepository.findByCode(permission).orElseThrow(()-> new ResourceNotFoundException("Permission non trouvée"));

        boolean hasPermission = userApp.getUserPermissions().stream()
                .anyMatch(up -> up.getPermission().getCode().equals(permission));

        if (hasPermission) {
            throw new IllegalStateException("L'utilisateur a déjà cette permission assignée.");
        }

            UserPermission userPermission = UserPermission.builder()
                        .user(userApp)
                        .permission(permissionOpt)
                        .modifiedBy("admin")
                        .modifiedAt(LocalDateTime.now())
                        .build();
            userPermissionRepository.save(userPermission);

            userApp.getUserPermissions().add(userPermission);
            userApp.setUpdatedAt(LocalDateTime.now());
            userAppRepository.save(userApp);
            return "Permission " + permission + " assignée à l'utilisateur avec succès.";
    }

    @Override
    @Transactional
    public String supprimerPermissionUtilisateur(Long idUtilisateur, String permission) {

        UserApp userApp = userAppRepository.findById(idUtilisateur)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        Permission permissionOpt = permissionRepository.findByCode(permission)
                .orElseThrow(() -> new ResourceNotFoundException("Permission non trouvée"));

        UserPermission userPermissionToDelete = userApp.getUserPermissions().stream()
                .filter(up -> up.getPermission().getCode().equals(permission))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("L'utilisateur n'a pas cette permission."));

        userPermissionRepository.delete(userPermissionToDelete);

        userApp.getUserPermissions().remove(userPermissionToDelete);
        userApp.setUpdatedAt(LocalDateTime.now());
        userAppRepository.save(userApp);

        return "Permission " + permission + " supprimée de l'utilisateur avec succès.";
    }

}
