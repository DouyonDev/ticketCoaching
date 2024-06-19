package com.odk.ticketcoaching.repository;

import com.odk.ticketcoaching.entity.Enum.Roles;
import com.odk.ticketcoaching.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    List<Utilisateur> findByRole(Roles role);
    Optional<Utilisateur> findByUsername(String username);
}
