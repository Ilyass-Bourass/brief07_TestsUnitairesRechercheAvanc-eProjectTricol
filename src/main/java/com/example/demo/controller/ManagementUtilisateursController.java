package com.example.demo.controller;


import com.example.demo.dto.managementRole.AssignerRoleRequest;
import com.example.demo.service.ManagementUtilisateursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/management-utilisateurs")
public class ManagementUtilisateursController {
    private final ManagementUtilisateursService managementUtilisateursService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Management Utilisateurs Endpoint is working!";
    }

    @PostMapping
    public ResponseEntity<String> assignRoleToUser(@Valid @RequestBody AssignerRoleRequest assignerRoleRequest) {
        String reslt= managementUtilisateursService.assignerRoleUtilisateur(
                assignerRoleRequest.getUserId(),
                assignerRoleRequest.getRole()
        );
        return new ResponseEntity<>(reslt, HttpStatus.OK);
    }

}
