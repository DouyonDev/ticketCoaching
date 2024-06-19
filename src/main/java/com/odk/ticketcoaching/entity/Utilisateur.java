package com.odk.ticketcoaching.entity;

import com.odk.ticketcoaching.entity.Enum.Roles;
import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
import lombok.Data;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank(message = "Le nom est obligatoire")
    private String nom;

    //@NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    //@NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    //@Email(message = "Veuillez entrer une adresse email valide")
    //@NotBlank(message = "L'email est obligatoire")
    private String email;

    // Utilisez BCrypt pour encoder le mot de passe avant de le stocker
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany
    private Set<Ticket> tickets;
    @OneToMany
    private Set<Notification> notifications;



    // public void hashMotDePasse(String motDePasse) {
     //   this.motDePasse = new BCryptPasswordEncoder().encode(motDePasse);
    //}



}
