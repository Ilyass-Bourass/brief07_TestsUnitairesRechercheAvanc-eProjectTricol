package com.example.demo.dto.managementRole;


import com.example.demo.entity.enums.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AssignerRoleRequest {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long userId;
    @NotBlank(message = "Le rôle est obligatoire")
    private String role;

}
