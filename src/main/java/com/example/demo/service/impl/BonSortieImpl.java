package com.example.demo.service.impl;

import com.example.demo.dto.BonSortie.RequestBonSortieDTO;
import com.example.demo.dto.BonSortie.ResponseBonSortieDTO;

import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.BonSortie;
import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.Produit;
import com.example.demo.entity.Stock;
import com.example.demo.entity.enums.StatutBonSortie;
import com.example.demo.entity.enums.TypeMouvement;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.BonSortieMapper;
import com.example.demo.mapper.StockVersMouvementStockMapper;
import com.example.demo.repository.BonSortieRepository;
import com.example.demo.repository.MouvementStockRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.BonSortieService;
import com.example.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor

public class BonSortieImpl implements BonSortieService {
    private final BonSortieMapper bonSortieMapper;
    private final BonSortieRepository bonSortieRepository;
    private final ProduitRepository produitRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;
    private final StockVersMouvementStockMapper  stockVersMouvementStockMapper;
    private final MouvementStockRepository mouvementStockRepository;

    @Override
    @Transactional
    public ResponseBonSortieDTO createBonSotie(RequestBonSortieDTO requestBonSortieDTO) {

        List<String> errors = new ArrayList<>();

        requestBonSortieDTO.getDetailsBonSortie().forEach(DetailsBonSortieDTO -> {
            if (!produitRepository.existsProduitById(DetailsBonSortieDTO.getProduitId())) {
                errors.add("Produit de id :" + DetailsBonSortieDTO.getProduitId() + " n'existe pas");
            }
        });

        if (!errors.isEmpty()) {
            throw new DuplicateResourceException(errors);
        }

        if (requestBonSortieDTO.getStatutBonSortie() == null) {
            requestBonSortieDTO.setStatutBonSortie(StatutBonSortie.BROULLION);
        }

        if (requestBonSortieDTO.getDateBonSortie() == null) {
            requestBonSortieDTO.setDateBonSortie(LocalDate.now());
        }

        BonSortie bonSortie = bonSortieMapper.toEntity(requestBonSortieDTO);

        if (bonSortie.getDetailsBonSortie() != null) {
            bonSortie.getDetailsBonSortie().forEach(detail -> detail.setBonSortie(bonSortie));
        }

        BonSortie savedBonSortie = bonSortieRepository.save(bonSortie);
        return bonSortieMapper.toDTO(savedBonSortie);
    }

    @Override
    public List<ResponseBonSortieDTO> findAllBonSortie() {
        List<ResponseBonSortieDTO> responseBonSortieDTOList = bonSortieRepository.findAll().stream().map(bonSortieMapper::toDTO).toList();
        return responseBonSortieDTOList;
    }

    @Override
    public ResponseBonSortieDTO findBonSortieById(Long id) {
        BonSortie bonSortie = bonSortieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Le bon de sortie de id: " + id + " n'existe pas"));

        return bonSortieMapper.toDTO(bonSortie);
    }

    @Override
    public void deleteBonSotie(Long id) {

    }

    @Override
    public ResponseBonSortieDTO updateBonSortie(Long id, RequestBonSortieDTO requestBonSortieDTO) {
        return null;
    }

    @Override
    public ResponseBonSortieDTO annulerBonSortie(Long id) {
        BonSortie bonSortie = bonSortieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Le bon de sortie de id: " + id + " n'existe pas"));
        if (bonSortie.getStatutBonSortie() != StatutBonSortie.BROULLION) {
            throw new ResourceNotFoundException("le bon de sortie de id : " + id + " n'est pas de statut brouillon\n vous n'avez pas la possiblité de annuler le bon de sortie");
        }
        bonSortie.setStatutBonSortie(StatutBonSortie.ANNULE);
        BonSortie savecBonSortie = bonSortieRepository.save(bonSortie);
        return bonSortieMapper.toDTO(savecBonSortie);
    }

    @Override
    public List<ResponseBonSortieDTO> findAllBonSortieByAtelier(String atelier) {
        if (!bonSortieRepository.existsBonSortieByAtelier(atelier)) {
            throw new ResourceNotFoundException("Cette atelier n'existe pas de nom : " + atelier);
        }
        List<ResponseBonSortieDTO> responseBonSortieDTOList = findAllBonSortie().stream().filter(bonSortie -> bonSortie.getAtelier().equals(atelier)).toList();
        return responseBonSortieDTOList;
    }

    @Override
    public boolean verfierBonsortieEtStockActuelProduit(Long idProduit, Integer quantiteBonSortie) {
        Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new ResourceNotFoundException("Produit de id : " + idProduit + " n'existe pas"));
        return produit.getStockActuel() >= quantiteBonSortie;
    }

    @Override
    public String ValiderBonSortie(Long idBonSortie) {

        BonSortie bonSortie = bonSortieRepository.findById(idBonSortie).orElseThrow(() -> new ResourceNotFoundException("Le bon de sortie de id: " + idBonSortie + " n'existe pas"));
        List<String> errors = new ArrayList<>();

        if (bonSortie.getStatutBonSortie() != StatutBonSortie.BROULLION) {
            throw new ResourceNotFoundException("le bon de sortie de id : " +idBonSortie  + " n'est pas de statut brouillon\n vous n'avez pas la possiblité de valider le bon de sortie");
        }

        bonSortie.getDetailsBonSortie().forEach(detailsBonSortie -> {
            if (!verfierBonsortieEtStockActuelProduit(detailsBonSortie.getProduit().getId(), detailsBonSortie.getQuantite())) {
                errors.add("❌ Erreur lors de bon de sortie de id: " + bonSortie.getId() + " car le stock actuel du produit de id : " + detailsBonSortie.getProduit().getId()
                        + " est : " + detailsBonSortie.getProduit().getStockActuel() + " et votre demande est : " + detailsBonSortie.getQuantite());
            }
        });

        if (!errors.isEmpty()) {
            throw new DuplicateResourceException(errors);
        }

        bonSortie.getDetailsBonSortie().forEach(detailsBonSortie -> {
            List<ResponseStockDTO> responseStockByProduitDTO=stockService.getStocksByProduit(detailsBonSortie.getProduit().getId());
            appliquerFifo(responseStockByProduitDTO, detailsBonSortie.getQuantite());
        });
        bonSortie.setStatutBonSortie(StatutBonSortie.VALIDE);
        bonSortieRepository.save(bonSortie);
        return "La validation du bon de sortie de id: "+bonSortie.getId()+" effuctué avec succée";
    }

    @Override
    @Transactional
    public void appliquerFifo(List<ResponseStockDTO> responseStockByProduitDTO, Integer quantiteBonSortie) {

        int quantiteRestant = quantiteBonSortie;

        for (ResponseStockDTO responseStockDTO : responseStockByProduitDTO) {

            if (quantiteRestant == 0) break;

            Stock stock = stockRepository.findById(responseStockDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Stock introuvable : " + responseStockDTO.getId()));

            Produit produit = produitRepository.findById(responseStockDTO.getProduitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + responseStockDTO.getProduitId()));

            int quantiteStock = stock.getQuantite();
            int quantiteMouvementStock;
            if (quantiteStock >= quantiteRestant) {
                stock.setQuantite(quantiteStock - quantiteRestant);
                produit.setStockActuel(produit.getStockActuel() - quantiteRestant);
                quantiteMouvementStock=quantiteRestant;
                quantiteRestant = 0;
            } else {
                quantiteMouvementStock=stock.getQuantite();
                stock.setQuantite(0);
                produit.setStockActuel(produit.getStockActuel() - quantiteStock);
                quantiteRestant -= quantiteStock;
            }

            Stock saved=stockRepository.save(stock);

            MouvementStock mouvementStock=stockVersMouvementStockMapper.stockVersMouvementStock(saved);
            mouvementStock.setTypeMouvement(TypeMouvement.SORTIE);
            mouvementStock.setQuantite(quantiteMouvementStock);
            mouvementStockRepository.save(mouvementStock);

            produitRepository.save(produit);
        }

        if (quantiteRestant > 0) {
            throw new ResourceNotFoundException(
                    "Stock insuffisant pour satisfaire la demande complète. Reste à sortir : " + quantiteRestant
            );
        }
    }

}
