package com.odk.ticketcoaching.controller;


import com.odk.ticketcoaching.entity.BaseConnaissance;
import com.odk.ticketcoaching.entity.Enum.Roles;
import com.odk.ticketcoaching.entity.Ticket;
import com.odk.ticketcoaching.entity.Utilisateur;
import com.odk.ticketcoaching.repository.BaseConnaissanceRepository;
import com.odk.ticketcoaching.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "End points pour les opérations menées par l'admin dans le system")
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;

    private Roles role;

    @Operation(summary = "Ajouter un admin dans la base de données")
    @PostMapping("/ajoutadmin")
    public ResponseEntity<Utilisateur> ajouterAdmin(@RequestBody Utilisateur admin, Principal principal) {
        Utilisateur savedAdmin = utilisateurService.creerAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @DeleteMapping("/SuppAdmin/{id}")
    public ResponseEntity<String> supprimerAdmin(@PathVariable int id) {
        utilisateurService.supprimerAdmin(id);
        return ResponseEntity.ok("Administrateur supprimer avec succes !!!");
    }

    @PostMapping("/formateurs")
    public ResponseEntity<Utilisateur> creerFormateur(@RequestBody Utilisateur formateur) {
        Utilisateur saveFormateur = utilisateurService.creerFormateur(formateur);
        return ResponseEntity.ok(saveFormateur);
    }

    @DeleteMapping("/formateurs/{id}")
    public ResponseEntity<Void> supprimerFormateur(@PathVariable int id) {
        utilisateurService.supprimerFormateur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/formateurs")
    public List<Utilisateur> listerFormateurs() {

        return utilisateurService.listerFormateurs();
    }
    @PostMapping("/insererBC")
    public ResponseEntity<BaseConnaissance> insertionBC(@RequestBody BaseConnaissance baseConnaissance) {
        BaseConnaissance saveQuestions = utilisateurService.insererBC(baseConnaissance);
        return ResponseEntity.ok(saveQuestions);
    }
    @GetMapping("/tickets")
    public List<Ticket> listerTickets() {
        return utilisateurService.listerTickets();
    }
    @GetMapping("/baseConnaissance")
    public List<BaseConnaissance> listerBC() {
        return utilisateurService.listerBaseConnaissance();
    }
}
