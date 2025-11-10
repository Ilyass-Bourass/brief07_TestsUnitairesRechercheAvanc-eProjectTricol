package com.example.demo.controller;

import com.example.demo.dto.BonSortie.RequestBonSortieDTO;
import com.example.demo.dto.BonSortie.ResponseBonSortieDTO;
import com.example.demo.service.BonSortieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bons-sortie")

public class BonSortieController {
    private final BonSortieService  bonSortieService;

    @GetMapping
    public ResponseEntity<List<ResponseBonSortieDTO>> findAllBonSortie() {
        return new ResponseEntity<>(bonSortieService.findAllBonSortie(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ResponseBonSortieDTO> creerBonSortie(@Valid @RequestBody RequestBonSortieDTO requestBonSortieDTO){
        ResponseBonSortieDTO responseBonSortieDTO = bonSortieService.createBonSotie(requestBonSortieDTO);
        return new ResponseEntity<>(responseBonSortieDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBonSortieDTO> getBonSortie(@PathVariable Long id){
        return new ResponseEntity<>(bonSortieService.findBonSortieById(id), HttpStatus.OK);
    }

    @PutMapping("{id}/annuler")
    public ResponseEntity<ResponseBonSortieDTO> annulerBonSortie(@PathVariable Long id){
        return new ResponseEntity<>(bonSortieService.annulerBonSortie(id), HttpStatus.OK);
    }

    @GetMapping("/atelier/{atelier}")
    public ResponseEntity<List<ResponseBonSortieDTO>> getBonSortieAtelier(@PathVariable("atelier") String atelier){
        return new ResponseEntity<>(bonSortieService.findAllBonSortieByAtelier(atelier), HttpStatus.OK);
    }
    @PutMapping("/{id}/valider")
    public ResponseEntity<String> validerBonSortie(@PathVariable Long id){
        String resultat = bonSortieService.ValiderBonSortie(id);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }
}
