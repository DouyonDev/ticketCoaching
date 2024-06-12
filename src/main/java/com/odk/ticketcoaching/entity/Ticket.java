package com.odk.ticketcoaching.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime; // Importez LocalDateTime pour la date de création

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La description est requise") // Validation avec Lombok
    private String description;

    private String categorie;

    private String Reponse;

    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreation; // Utilisez LocalDateTime

    private boolean resolu;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    // Assurez-vous que la classe Utilisateur a une annotation @OneToMany pour cette relation
}