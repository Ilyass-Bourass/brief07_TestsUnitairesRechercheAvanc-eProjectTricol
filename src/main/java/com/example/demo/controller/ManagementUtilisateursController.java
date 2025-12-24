package com.example.demo.controller;


import com.example.demo.dto.managementRole.AjouterOuSupprmierPermisionUser;
import com.example.demo.dto.managementRole.AssignerRoleRequest;
import com.example.demo.service.ManagementUtilisateursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/management-utilisateurs")


@PreAuthorize("hasAuthority('ASSIGNER_ROLE')")

public class ManagementUtilisateursController {
    private final ManagementUtilisateursService managementUtilisateursService;

    @PostMapping("/assigner-role")
    public ResponseEntity<String> assignRoleToUser(@Valid @RequestBody AssignerRoleRequest assignerRoleRequest) {
        String reslt= managementUtilisateursService.assignerRoleUtilisateur(
                assignerRoleRequest.getUserId(),
                assignerRoleRequest.getRole()
        );
        return new ResponseEntity<>(reslt, HttpStatus.OK);
    }

    @PostMapping("/assigner-permission")
    public ResponseEntity<String> assignPermissionToUser(@Valid @RequestBody AjouterOuSupprmierPermisionUser request) {
        String result = managementUtilisateursService.assignerPermissionUtilisateur(
                request.getUserId(),
                request.getPermission()
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/supprimer-permission")
    public ResponseEntity<String> removePermissionFromUser(@Valid @RequestBody AjouterOuSupprmierPermisionUser request) {
        String result = managementUtilisateursService.supprimerPermissionUtilisateur(
                request.getUserId(),
                request.getPermission()
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
