package com.odk.ticketcoaching.controller;


import com.odk.ticketcoaching.entity.Ticket;
import com.odk.ticketcoaching.entity.Utilisateur;
import com.odk.ticketcoaching.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formateur")
public class FormateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/apprenants")
    public ResponseEntity<Utilisateur> ajouterApprenant(@RequestBody Utilisateur apprenant) {
        Utilisateur savedApprenant = utilisateurService.creerApprenant(apprenant);
        return ResponseEntity.ok(savedApprenant);
    }

    @DeleteMapping("/apprenants/{id}")
    public ResponseEntity<Void> supprimerApprenant(@PathVariable int id) {
        utilisateurService.supprimerApprenant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/apprenants")
    public List<Utilisateur> listerApprenants() {
        return utilisateurService.listerApprenants();
    }

    @PostMapping("/tickets/{id}/repondre")
    public Ticket repondreTicket(@PathVariable int id, @RequestBody String reponse) {
        return utilisateurService.repondreTicket(id, reponse);
    }

    @GetMapping("/tickets")
    public List<Ticket> listerTickets() {
        return utilisateurService.listerTickets();
    }
}
