package com.example.demo.service.impl;

import com.example.demo.dto.commande.RequestCommandeDTO;
import com.example.demo.dto.commande.ResponseCommandeDTO;
import com.example.demo.entity.*;
import com.example.demo.entity.enums.StatutCommande;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.*;
import com.example.demo.repository.*;
import com.example.demo.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;
    private final StockRepository stockRepository;
    private final MouvementStockRepository mouvementStockRepository;
    private final StockVersMouvementStockMapper  stockVersMouvementStockMapper;
    private final CommandeMapper commandeMapper;
    private final DetailsCommandeVersStockMapper detailsCommandeVersStockMapper;

    @Override
    @Transactional
    public ResponseCommandeDTO createCommande(RequestCommandeDTO requestDTO) {

        if(requestDTO == null){
            throw new IllegalArgumentException("La commande ne peut pas être nulle");
        }
        Fournisseur fournisseur = fournisseurRepository.findById(requestDTO.getFournisseurId())
                .orElseThrow(() -> new IllegalArgumentException("Fournisseur introuvable"));
        Commande commande =new Commande();
        commande.setDateCommande(requestDTO.getDateCommande());
        commande.setStatutCommande(requestDTO.getStatutCommande());
        commande.setFournisseur(fournisseur);

        List<CommandeProduit> commandeProduits =requestDTO.getProduits().stream()
                .map(produitDto->{
                    Produit produit =produitRepository.findById(produitDto.getId()).orElseThrow(() -> new IllegalArgumentException("Produits introuvable"));
                    CommandeProduit cp = new CommandeProduit();
                    cp.setCommande(commande);
                    cp.setProduit(produit);
                    cp.setQuantite(produitDto.getQuantite());
                    cp.setPrixUnitaire(produitDto.getPrixUnitaire());
                    return cp;
                }).toList();

        double montantTotal =commandeProduits.stream().mapToDouble(cm->cm.getQuantite()*cm.getPrixUnitaire()).sum();
        commande.setMontantTotal(montantTotal);
        commande.setCommandeProduits(commandeProduits);

        Commande savedCommande = commandeRepository.save(commande);

        return commandeMapper.toResponseDTO(savedCommande);
    }

    @Transactional(readOnly = true)

    @Override
    public List<ResponseCommandeDTO> findAllCommandes() {
        return commandeRepository.findAll().stream().map(commandeMapper::toResponseDTO).toList();
    }

    @Override
    public ResponseCommandeDTO findCommandeById(Long id) {

        return commandeMapper.toResponseDTO(commandeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Commande n'existe pas de id :"+id))) ;
    }

    @Override
    @Transactional
    public ResponseCommandeDTO updateCommande(Long id, RequestCommandeDTO requestDTO) {

        if(requestDTO == null){
            throw new IllegalArgumentException("La commande ne peut pas être nulle");
        }

        Commande existingCommande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec id: " + id));

        Fournisseur fournisseur = fournisseurRepository.findById(requestDTO.getFournisseurId())
                .orElseThrow(() -> new IllegalArgumentException("Fournisseur introuvable"));

        existingCommande.setDateCommande(requestDTO.getDateCommande());
        existingCommande.setStatutCommande(requestDTO.getStatutCommande());
        existingCommande.setFournisseur(fournisseur);

        List<CommandeProduit> commandeProduits = new ArrayList<>(
                requestDTO.getProduits().stream()
                        .map(produitDto -> {
                            Produit produit = produitRepository.findById(produitDto.getId())
                                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable avec id: " + produitDto.getId()));
                            CommandeProduit cp = new CommandeProduit();
                            cp.setCommande(existingCommande);
                            cp.setProduit(produit);
                            cp.setQuantite(produitDto.getQuantite());
                            cp.setPrixUnitaire(produitDto.getPrixUnitaire());
                            return cp;
                        }).toList()
        );

        double montantTotal = commandeProduits.stream()
                .mapToDouble(cp -> cp.getQuantite() * cp.getPrixUnitaire())
                .sum();

        existingCommande.setMontantTotal(montantTotal);
        existingCommande.setCommandeProduits(commandeProduits);

        Commande updatedCommande = commandeRepository.save(existingCommande);

        return commandeMapper.toResponseDTO(updatedCommande);
    }


    @Override
    public void deleteCommande(Long id) {
        if(!commandeRepository.existsById(id)){
            throw new ResourceNotFoundException("Commande n'existe pas de id : "+id);
        }
        commandeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void validerCommande(Long id) {

        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Commande n'existe pas de id :"+id));

            if(!commande.getStatutCommande().equals(StatutCommande.EN_ATTENTE)){
                throw new IllegalArgumentException("StatutCommande pas en attente donc vous avez pas la possiblité de validee : "+id);
            }

         commande.getCommandeProduits().forEach(commandeProduit -> {
            if(commandeProduit == null){
                throw new IllegalArgumentException("commande produit introuvable");
            }

            Produit produit = produitRepository.findById(commandeProduit.getProduit().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable"));

             Stock stock = detailsCommandeVersStockMapper.detailsCommandeToStock(commandeProduit, commande, produit);
             Stock savedStock = stockRepository.save(stock);

             MouvementStock mouvementStock = stockVersMouvementStockMapper.stockVersMouvementStock(savedStock);
             mouvementStockRepository.save(mouvementStock);

             produit.setStockActuel(produit.getStockActuel() + commandeProduit.getQuantite());

        });
            commande.setStatutCommande(StatutCommande.VALIDEE);
            commandeRepository.save(commande);
    }
}
