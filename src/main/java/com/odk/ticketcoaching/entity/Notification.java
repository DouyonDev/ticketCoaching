package com.odk.ticketcoaching.entity;

import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime; // Importez LocalDateTime pour la date d'envoi

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank(message = "Le message est requis") // Validation avec Lombok
    private String message;

    @Column(columnDefinition = "TIMESTAMP") // Utilisez TIMESTAMP pour stocker la date d'envoi
    private LocalDateTime dateEnvoi;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Ticket ticket;
}
