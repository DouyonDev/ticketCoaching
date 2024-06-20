package com.odk.ticketcoaching.entity;

import com.odk.ticketcoaching.entity.Enum.Categories;
import com.odk.ticketcoaching.entity.Enum.Priorites;
import com.odk.ticketcoaching.entity.Enum.Statuts;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime; // Importez LocalDateTime pour la date de création

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank(message = "La description est requise") // Validation avec Lombok
    private String description;

    @Enumerated(EnumType.STRING)
    private Categories categorie;

    private String Reponse;

    @Enumerated(EnumType.STRING)
    private Priorites priorite;

    @Enumerated(EnumType.STRING)
    private Statuts statut;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreation; // Utilisez LocalDateTime

    private boolean resolu=false;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    // Assurez-vous que la classe Utilisateur a une annotation @OneToMany pour cette relation
}
