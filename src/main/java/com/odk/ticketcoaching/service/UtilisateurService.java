package com.odk.ticketcoaching.service;


import ch.qos.logback.classic.encoder.JsonEncoder;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.odk.ticketcoaching.entity.*;
import com.odk.ticketcoaching.entity.Enum.Roles;
import com.odk.ticketcoaching.entity.Enum.Statuts;
import com.odk.ticketcoaching.repository.BaseConnaissanceRepository;
import com.odk.ticketcoaching.repository.NotificationRepository;
import com.odk.ticketcoaching.repository.TicketRepository;
import com.odk.ticketcoaching.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BaseConnaissanceRepository baseConnaissanceRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public Utilisateur creerAdmin(Utilisateur Admin) {

        Admin.setRole(Roles.ADMIN);
        Admin.setMotDePasse(passwordEncoder.encode(Admin.getMotDePasse()));

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
        formateur.setRole(Roles.FORMATEUR);

        formateur.setMotDePasse(passwordEncoder.encode(formateur.getMotDePasse()));

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
        apprenant.setRole(Roles.APPRENANT);
        apprenant.setMotDePasse(passwordEncoder.encode(apprenant.getMotDePasse()));
        return utilisateurRepository.save(apprenant);
    }


    public void supprimerApprenant(int id) {
        Utilisateur apprenant = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apprenant non trouvé"));
        if (!apprenant.getRole().equals(Roles.APPRENANT)) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un apprenant");
        }else {
            // Mettre à jour les tickets pour dissocier l'utilisateur
            List<Ticket> tickets = ticketRepository.findByUtilisateur(apprenant);
            for (Ticket ticket : tickets) {

                ticket.setUtilisateur(null);
            }
            ticketRepository.saveAll(tickets);
            utilisateurRepository.deleteById(id);
        }
    }

    public List<Utilisateur> listerApprenants() {
        return utilisateurRepository.findByRole(Roles.APPRENANT);
    }

    // Méthodes pour gérer les tickets (accessible par les formateurs et apprenants)

    public Ticket creerTicket(Ticket ticket, int utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Apprenant non trouvé"));

        ticket.setDateCreation(LocalDateTime.now());
        ticket.setStatut(Statuts.EN_COURS);
        ticket.setUtilisateur(utilisateur);
        Ticket enrTicket = ticketRepository.save(ticket);
        List<Utilisateur> utilisateurs = utilisateurRepository.findByRole(Roles.FORMATEUR);
        for (Utilisateur utilisateur1 : utilisateurs) {
            // Envoyer une notification aux formateurs
            String emailformateur = utilisateur1.getEmail();
            String subject = "Un nouveau ticket à été ajouter par l'apprenant : "+utilisateur.getUsername();
            String message = "Bonjour "+utilisateur1.getUsername() + ",\n\nUn ticket a été ajouter par : \""
                    + utilisateur.getUsername()
                    + "\", nous avons besoin de votre expertise.\n\nDescription : " + ticket.getDescription()
                    + "\n\nPriorité : " + ticket.getPriorite()
                    + "\n\nCategorie : " + ticket.getCategorie();
            emailService.sendSimpleMessage(emailformateur,subject,message);
        }
        return enrTicket;
    }

    public void supprimerTicket(int id,String currentUsername) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        if (!ticket.getUtilisateur().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("Un ticket est supprimer que par l'apprenant qui la creer");
        }
        ticketRepository.deleteById(id);
    }

    public Ticket repondreTicket(int ticketId, String reponse) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        ticket.setReponse(reponse);
        ticket.setStatut(Statuts.REPONDU);
        // Envoyer une notification à l'apprenant
        String emailApprenant = ticket.getUtilisateur().getEmail();
        String subject = "Votre ticket a été répondu";
        String message = "Bonjour " + ticket.getUtilisateur().getPrenom() + ",\n\nVotre ticket avec la description : \"" + ticket.getDescription() + "\" a été répondu.\n\nRéponse : " + reponse;
        emailService.sendSimpleMessage(emailApprenant, subject, message);
        return ticketRepository.save(ticket);
    }

    public Ticket marquerTicketCommeResolu(int ticketId, boolean resolu) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        ticket.setResolu(resolu);

        /*if (resolu) {
            BaseConnaissance baseConnaissance = new BaseConnaissance();
            baseConnaissance.setQuestion(ticket.getDescription());
            baseConnaissance.setReponse(ticket.getReponse());
            baseConnaissanceRepository.save(baseConnaissance);
        }*/
        return ticketRepository.save(ticket);
    }

    public List<Ticket> listerTickets() {
        return ticketRepository.findAll();
    }

    public Ticket ModifierTicket(int ticketId, Ticket nouveauTicket, String currentUsername) throws AccessDeniedException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket non existant pour l'id :: " + ticketId));

        if (!ticket.getUtilisateur().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("Seul l'apprenant qui a creer ce ticket peut le modifier !!!");
        }

        if (nouveauTicket.getDescription()!=null && nouveauTicket.getCategorie()!=ticket.getCategorie() && nouveauTicket.getPriorite()!=ticket.getPriorite()) {
            ticket.setDescription(nouveauTicket.getDescription());
                ticket.setCategorie(nouveauTicket.getCategorie());
                ticket.setPriorite(nouveauTicket.getPriorite());
            }
        return ticketRepository.save(ticket);
    }

    public BaseConnaissance insererBC(BaseConnaissance baseConnaissance){
        return baseConnaissanceRepository.save(baseConnaissance);
    }

    public List<BaseConnaissance> listerBaseConnaissance() {
        return baseConnaissanceRepository.findAll();
    }



}
