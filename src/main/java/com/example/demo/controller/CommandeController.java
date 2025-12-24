package com.example.demo.controller;


import com.example.demo.dto.commande.RequestCommandeDTO;
import com.example.demo.dto.commande.ResponseCommandeDTO;
import com.example.demo.entity.Commande;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.service.CommandeService;
import com.example.demo.service.impl.CommandeServiceImpl;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commandes")
@RequiredArgsConstructor
public class CommandeController {
    private final CommandeServiceImpl commandeService;

    @PreAuthorize("hasAuthority('COMMANDE_CREATE')")
    @PostMapping
    public ResponseEntity<ResponseCommandeDTO> ajouterCommande(@Valid @RequestBody RequestCommandeDTO commandeDTO) {
        ResponseCommandeDTO responseCommandeDTO = commandeService.createCommande(commandeDTO);
        return new ResponseEntity<>(responseCommandeDTO, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('COMMANDE_READ')")
    @GetMapping
    public ResponseEntity<List<ResponseCommandeDTO>> findAll(){
        return new ResponseEntity<>(commandeService.findAllCommandes(), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('COMMANDE_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCommandeDTO> findById(@PathVariable Long id){
         return  new ResponseEntity<>(commandeService.findCommandeById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('COMMANDE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseCommandeDTO> updateCommande(
            @PathVariable Long id,
            @Valid @RequestBody RequestCommandeDTO commandeDTO) {

        ResponseCommandeDTO updatedCommande = commandeService.updateCommande(id, commandeDTO);
        return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('COMMANDE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable Long id){
        commandeService.deleteCommande(id);
        return new ResponseEntity<>("la suppression avec id : "+id+" se fait avec succée",HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('COMMANDE_VALIDATE')")
    @PutMapping("/validerCommande/{id}")
    public ResponseEntity<String> validerCommandeById(@PathVariable Long id){
        commandeService.validerCommande(id);
        return new ResponseEntity<>("l'ajout des stock et mouvement se fait avec succées",HttpStatus.OK);
    }

}
