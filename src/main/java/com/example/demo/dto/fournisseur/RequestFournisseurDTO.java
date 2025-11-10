package com.example.demo.dto.fournisseur;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFournisseurDTO {

    @NotBlank(message = "La raison sociale du fournisseur est obligatoire")
    private String raisonSociale;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "La personne de contact est obligatoire")
    private String personneContact;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email non valide")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "L'ICE est obligatoire")
    private String ice;
}
