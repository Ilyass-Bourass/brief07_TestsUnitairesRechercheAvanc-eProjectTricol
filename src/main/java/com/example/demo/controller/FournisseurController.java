package com.example.demo.controller;


import com.example.demo.dto.fournisseur.RequestFournisseurDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.service.impl.FournisseurServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fournisseurs")
@RequiredArgsConstructor

public class FournisseurController {
    private final FournisseurServiceImpl fournisseurService;

    @PostMapping
    public ResponseEntity<ResponseFournisseurDTO> createFournisseur(@Valid @RequestBody RequestFournisseurDTO requestdto)
    {
        ResponseFournisseurDTO response = fournisseurService.createFournisseur(requestdto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseFournisseurDTO>>  getALLFournisseur(){
        List<ResponseFournisseurDTO> fournisseursDTO = fournisseurService.FindALLFournisseur();
        return new ResponseEntity<>(fournisseursDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseFournisseurDTO>  getFournisseur(@PathVariable Long id){
        ResponseFournisseurDTO response = fournisseurService.findFournisserById(id);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseFournisseurDTO> updateFournisseur(@PathVariable Long id, @RequestBody RequestFournisseurDTO requestDTO){
        ResponseFournisseurDTO responseFournisseurDTO =fournisseurService.updateFournisseur(id, requestDTO);
        return new ResponseEntity<>(responseFournisseurDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFournisseur(@PathVariable Long id){
        fournisseurService.deleteFournisseur(id);
        return new ResponseEntity<>("Le fournisseur à été supprimer avec succées de id : "+id,HttpStatus.NO_CONTENT);
    }
}
