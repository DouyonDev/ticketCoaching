package com.odk.ticketcoaching.controller;


import com.odk.ticketcoaching.entity.Ticket;
import com.odk.ticketcoaching.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apprenant")
public class ApprenantController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> creerTicket(@RequestBody Ticket ticket, @RequestParam int utilisateurId) {
        Ticket nouveauTicket = utilisateurService.creerTicket(ticket, utilisateurId);
        return new ResponseEntity<>(nouveauTicket, HttpStatus.CREATED);
    }

    @GetMapping("/tickets")
    public List<Ticket> listerTickets() {
        return utilisateurService.listerTickets();
    }

    @DeleteMapping("/SuppTicket/{id}")
    public ResponseEntity<Void> supprimerTicket(@PathVariable int id) {
        utilisateurService.supprimerTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tickets/{id}/resolu")
    public Ticket ticketRepondu(@PathVariable int id) {
        return utilisateurService.marquerTicketCommeResolu(id, true);
    }
}
