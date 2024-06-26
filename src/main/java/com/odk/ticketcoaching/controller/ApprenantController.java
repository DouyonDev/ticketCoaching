package com.odk.ticketcoaching.controller;


import com.odk.ticketcoaching.entity.BaseConnaissance;
import com.odk.ticketcoaching.entity.Ticket;
import com.odk.ticketcoaching.entity.Utilisateur;
import com.odk.ticketcoaching.repository.UtilisateurRepository;
import com.odk.ticketcoaching.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/apprenant")
public class ApprenantController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> creerTicket(@RequestBody Ticket ticket) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);

        if (utilisateur == null) {
            return ResponseEntity.status(404).body(null);
        }
        Ticket nouveauTicket = utilisateurService.creerTicket(ticket,utilisateur.getId().intValue());
        return ResponseEntity.ok(nouveauTicket);
    }

    @GetMapping("/tickets")
    public List<Ticket> listerTickets() {
        return utilisateurService.listerTickets();
    }

    @DeleteMapping("/SuppTicket/{id}")
    public ResponseEntity<Void> supprimerTicket(@PathVariable int id,Principal principal) {
        String currentUsername = principal.getName();
        utilisateurService.supprimerTicket(id,currentUsername);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tickets/{id}/Resolu")
    public Ticket ticketResolu(@PathVariable int id) {
        return utilisateurService.marquerTicketCommeResolu(id, true);
    }


    @PutMapping("/modifierTicket/{id}")
    public ResponseEntity<Ticket> modifierTicket(@PathVariable(value = "id") int ticketId, @RequestBody Ticket ticketDetails, Principal principal) {
        String currentUsername = principal.getName();
        Ticket updatedTicket =utilisateurService.ModifierTicket(ticketId, ticketDetails, currentUsername);
        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/baseConnaissance")
    public List<BaseConnaissance> listerBC() {
        return utilisateurService.listerBaseConnaissance();
    }
}
