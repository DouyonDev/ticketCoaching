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

    private String description;

    @Enumerated(EnumType.STRING)
    private Categories categorie;

    private String Reponse;

    @Enumerated(EnumType.STRING)
    private Priorites priorite;

    @Enumerated(EnumType.STRING)
    private Statuts statut;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreation;

    private boolean resolu=false;

    @ManyToOne
    //@JoinColumn(name = "utilisateur_id")
    //@Column(insertable=false, updatable=false)
    private Utilisateur apprenant;

    @ManyToOne
    //@JoinColumn(name = "utilisateur_id")
    //@Column(insertable=false, updatable=false)
    private Utilisateur formateur;

}
