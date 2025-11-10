package com.example.demo.service;

import com.example.demo.dto.commande.RequestCommandeDTO;
import com.example.demo.dto.commande.ResponseCommandeDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;

import java.util.List;

public interface CommandeService {
    ResponseCommandeDTO createCommande(RequestCommandeDTO requestDTO);
    List<ResponseCommandeDTO> findAllCommandes();
    ResponseCommandeDTO findCommandeById(Long id);
    ResponseCommandeDTO updateCommande(Long id, RequestCommandeDTO commandeDTO);
    void deleteCommande(Long id);
    void validerCommande(Long id);
}
