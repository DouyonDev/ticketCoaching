package com.odk.ticketcoaching.controller;


import com.odk.ticketcoaching.entity.Utilisateur;
import com.odk.ticketcoaching.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/ajoutadmin")
    public ResponseEntity<Utilisateur> ajouterAdmin(@RequestBody Utilisateur admin) {
        Utilisateur savedAdmin = utilisateurService.creerAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @DeleteMapping("/SuppAdmin/{id}")
    public ResponseEntity<Void> supprimerAdmin(@PathVariable int id) {
        utilisateurService.supprimerAdmin(id);
        return ResponseEntity.noContent().build();
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
}
