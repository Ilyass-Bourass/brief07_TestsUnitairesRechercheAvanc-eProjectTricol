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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commandes")
@RequiredArgsConstructor
public class CommandeController {
    private final CommandeServiceImpl commandeService;


    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody String body) {
        System.out.println("Body reçu : " + body);
        return ResponseEntity.ok("Reçu !");
    }


    @PostMapping
    public ResponseEntity<ResponseCommandeDTO> ajouterCommande(@Valid @RequestBody RequestCommandeDTO commandeDTO) {
        ResponseCommandeDTO responseCommandeDTO = commandeService.createCommande(commandeDTO);
        return new ResponseEntity<>(responseCommandeDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCommandeDTO>> findAll(){
        return new ResponseEntity<>(commandeService.findAllCommandes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCommandeDTO> findById(@PathVariable Long id){
         return  new ResponseEntity<>(commandeService.findCommandeById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCommandeDTO> updateCommande(
            @PathVariable Long id,
            @Valid @RequestBody RequestCommandeDTO commandeDTO) {

        ResponseCommandeDTO updatedCommande = commandeService.updateCommande(id, commandeDTO);
        return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable Long id){
        commandeService.deleteCommande(id);
        return new ResponseEntity<>("la suppression avec id : "+id+" se fait avec succée",HttpStatus.OK);
    }

    @PutMapping("/validerCommande/{id}")
    public ResponseEntity<String> validerCommandeById(@PathVariable Long id){
        commandeService.validerCommande(id);
        return new ResponseEntity<>("l'ajout des stock et mouvement se fait avec succées",HttpStatus.OK);
    }

}
