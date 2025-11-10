package com.example.demo.dto.fournisseur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFournisseurDTO {

    private Long id;
    private String raisonSociale;
    private String adresse;
    private String personneContact;
    private String email;
    private String telephone;
    private String ville;
    private String ice;
}
