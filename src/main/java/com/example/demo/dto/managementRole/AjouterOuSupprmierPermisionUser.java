package com.example.demo.dto.managementRole;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AjouterOuSupprmierPermisionUser {
    private Long userId;
    private String permission;
}
