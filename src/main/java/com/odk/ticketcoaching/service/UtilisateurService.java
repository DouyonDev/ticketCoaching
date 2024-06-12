package com.odk.ticketcoaching.service;


import com.odk.ticketcoaching.entity.*;
import com.odk.ticketcoaching.repository.BaseConnaissanceRepository;
import com.odk.ticketcoaching.repository.TicketRepository;
import com.odk.ticketcoaching.repository.UtilisateurRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // Méthodes pour gérer les formateurs (accessible par les admins)
    public Utilisateur creerFormateur(Utilisateur formateur) {
        if (!formateur.getRoles().equals(Role.FORMATEUR)) {
            throw new IllegalArgumentException("Le rôle de l'utilisateur doit être FORMATEUR");
        }
        return utilisateurRepository.save(formateur);
    }

    public void supprimerFormateur(int id) {
        Utilisateur formateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur non trouvé"));
        if (!formateur.getRoles().equals(Role.FORMATEUR)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un formateur");
        }
        utilisateurRepository.deleteById(id);
    }

    public List<Utilisateur> listerFormateurs() {
        return utilisateurRepository.findByRole(Role.FORMATEUR);
    }

    // Méthodes pour gérer les apprenants (accessible par les formateurs)
    public Utilisateur creerApprenant(Utilisateur apprenant) {
        if (!apprenant.getRoles().equals(Role.APPRENANT)) {
            throw new IllegalArgumentException("Le rôle de l'utilisateur doit être APPRENANT");
        }
        return utilisateurRepository.save(apprenant);
    }

    public void supprimerApprenant(int id) {
        Utilisateur apprenant = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apprenant non trouvé"));
        if (!apprenant.getRoles().equals(Role.APPRENANT)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un apprenant");
        }
        utilisateurRepository.deleteById(id);
    }

    public List<Utilisateur> listerApprenants() {
        return utilisateurRepository.findByRole(Role.APPRENANT);
    }

    // Méthodes pour gérer les tickets (accessible par les formateurs et apprenants)
    public Ticket creerTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket repondreTicket(int ticketId, String reponse) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        ticket.setReponse(reponse);
        ticket.setStatut(Statut.REPONDU);
        return ticketRepository.save(ticket);
    }

    public Ticket marquerTicketCommeResolu(int ticketId, boolean resolu) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        ticket.setResolu(resolu);

        if (resolu) {
            BaseConnaissance baseConnaissance = new BaseConnaissance();
            baseConnaissance.setQuestion(ticket.getDescription());
            baseConnaissance.setReponse(ticket.getReponse());
            baseConnaissance.save(baseConnaissance);


        return ticketRepository.save(ticket);
    }
    public List<Ticket> listerTickets() {
        return ticketRepository.findAll();
    }
}
