package com.odk.ticketcoaching;

import com.odk.ticketcoaching.entity.Utilisateur;
import com.odk.ticketcoaching.entity.Enum.Roles;
import com.odk.ticketcoaching.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class TicketCoachingApplication implements CommandLineRunner {

	@Autowired
	private UtilisateurService utilisateurService;

	public static void main(String[] args) {
		SpringApplication.run(TicketCoachingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner entre = new Scanner(System.in);
		System.out.println("Ajout de l'admin : ");
		System.out.println("Donnez votre nom");
		String nom = entre.nextLine();
		System.out.println("Donnez votre prenom");
		String prenom = entre.nextLine();
		System.out.println("Donnez votre username");
		String username = entre.nextLine();
		System.out.println("Donnez votre email");
		String email = entre.nextLine();
		System.out.println("Votre mot de passe");
		String motDePasse = entre.nextLine();

		Utilisateur admin = new Utilisateur();
		admin.setNom(nom);
		admin.setPrenom(prenom);
		admin.setUsername(username);
		admin.setEmail(email);
		admin.setMotDePasse(motDePasse);
		admin.setRole(Roles.FORMATEUR);

		utilisateurService.creerFormateur(admin);
	}
}
