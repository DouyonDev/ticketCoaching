package com.odk.ticketcoaching.controller;


import com.odk.ticketcoaching.entity.Ticket;
import com.odk.ticketcoaching.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apprenant")
public class ApprenantController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/tickets")
    public Ticket creerTicket(@RequestBody Ticket ticket) {
        return utilisateurService.creerTicket(ticket);
    }

    @GetMapping("/tickets")
    public List<Ticket> listerTickets() {
        return utilisateurService.listerTickets();
    }
}
