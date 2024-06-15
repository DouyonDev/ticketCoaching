package com.odk.ticketcoaching.controller;


import com.odk.ticketcoaching.entity.Utilisateur;
import com.odk.ticketcoaching.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/formateurs")
    public Utilisateur creerFormateur(@RequestBody Utilisateur formateur) {
        return utilisateurService.creerFormateur(formateur);
    }

    @DeleteMapping("/formateurs/id")
    public void supprimerFormateur(@PathVariable int id) {
        utilisateurService.supprimerFormateur(id);
    }

    @GetMapping("/formateurs")
    public List<Utilisateur> listerFormateurs() {
        return utilisateurService.listerFormateurs();
    }
}
