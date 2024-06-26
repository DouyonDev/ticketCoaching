package com.odk.ticketcoaching.controller;

import com.odk.ticketcoaching.entity.Utilisateur;
import com.odk.ticketcoaching.repository.UtilisateurRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return ResponseEntity.ok(utilisateurRepository.save(utilisateur));
    }*/

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(loginRequest.getUsername());
        if (utilisateur == null || !passwordEncoder.matches(loginRequest.getPassword(), utilisateur.getMotDePasse())) {
            return ResponseEntity.status(401).body("Nom d'utilisateur ou mot de passse incorrect");
        }
        return ResponseEntity.ok("Connexion réussie");
    }
}
@Data
class LoginRequest {
    private String username;
    private String password;
}
