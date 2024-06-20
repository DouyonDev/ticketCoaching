package com.odk.ticketcoaching.service;


import ch.qos.logback.classic.encoder.JsonEncoder;
import com.odk.ticketcoaching.entity.*;
import com.odk.ticketcoaching.entity.Enum.Roles;
import com.odk.ticketcoaching.entity.Enum.Statuts;
import com.odk.ticketcoaching.repository.BaseConnaissanceRepository;
import com.odk.ticketcoaching.repository.TicketRepository;
import com.odk.ticketcoaching.repository.UtilisateurRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BaseConnaissanceRepository baseConnaissanceRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    public Utilisateur creerAdmin(Utilisateur Admin) {
        if (!Admin.getRole().equals(Roles.ADMIN)) {
          throw new IllegalArgumentException("Ce lien est pour l'insertion des admins");
        }
        //Admin.setMotDePasse(passwordEncoder.encode(Admin.getMotDePasse()));

        return utilisateurRepository.save(Admin);
    }

    public void supprimerAdmin(int id) {
        Utilisateur Admin = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));
        if (!Admin.getRole().equals(Roles.ADMIN)) {
          throw new IllegalArgumentException("L'utilisateur n'est pas un Administrateur");
        }else utilisateurRepository.deleteById(id);
    }

    // Méthodes pour gérer les formateurs (accessible par les admins)
    public Utilisateur creerFormateur(Utilisateur formateur) {
        if (!formateur.getRole().equals(Roles.FORMATEUR)) {
            throw new IllegalArgumentException("Le rôle de l'utilisateur doit être FORMATEUR");
        }

        //formateur.setMotDePasse(passwordEncoder.encode(formateur.getMotDePasse()));

        return utilisateurRepository.save(formateur);
    }

    public void supprimerFormateur(int id) {
        Utilisateur formateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur non trouvé"));
        if (!formateur.getRole().equals(Roles.FORMATEUR)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un formateur");
        }else utilisateurRepository.deleteById(id);
    }

    public List<Utilisateur> listerFormateurs() {
        return utilisateurRepository.findByRole(Roles.FORMATEUR);
    }

    // Méthodes pour gérer les apprenants (accessible par les formateurs)
    public Utilisateur creerApprenant(Utilisateur apprenant) {
        if (!apprenant.getRole().equals(Roles.APPRENANT)) {
            throw new IllegalArgumentException("Le rôle de l'utilisateur doit être APPRENANT");
        }

        //apprenant.setMotDePasse(passwordEncoder.encode(apprenant.getMotDePasse()));
        return utilisateurRepository.save(apprenant);
    }

    public void supprimerApprenant(int id) {
        Utilisateur apprenant = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apprenant non trouvé"));
        if (!apprenant.getRole().equals(Roles.APPRENANT)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un apprenant");
        }else utilisateurRepository.deleteById(id);
    }

    public List<Utilisateur> listerApprenants() {
        return utilisateurRepository.findByRole(Roles.APPRENANT);
    }

    // Méthodes pour gérer les tickets (accessible par les formateurs et apprenants)

    public Ticket creerTicket(Ticket ticket, int utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        ticket.setUtilisateur(utilisateur);
        return ticketRepository.save(ticket);
    }

    public void supprimerTicket(int id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        ticketRepository.deleteById(id);
    }

    public Ticket repondreTicket(int ticketId, String reponse) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        ticket.setReponse(reponse);
        ticket.setStatut(Statuts.REPONDU);
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
            baseConnaissanceRepository.save(baseConnaissance);
        }
        return ticketRepository.save(ticket);
    }

    public List<Ticket> listerTickets() {
        return ticketRepository.findAll();
    }
}
