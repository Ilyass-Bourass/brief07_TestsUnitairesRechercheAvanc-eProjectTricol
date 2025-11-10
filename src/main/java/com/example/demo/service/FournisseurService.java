package com.example.demo.service;

import com.example.demo.dto.fournisseur.RequestFournisseurDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;

import java.util.List;

public interface FournisseurService {
    ResponseFournisseurDTO createFournisseur(RequestFournisseurDTO requestDTO);
    List<ResponseFournisseurDTO> FindALLFournisseur();
    ResponseFournisseurDTO findFournisserById(Long id);
    ResponseFournisseurDTO updateFournisseur(Long id, RequestFournisseurDTO fournisseurDTO);
    void deleteFournisseur(Long id);
    public boolean emailExists(String email);
    public boolean iceExists(String ice);
}
