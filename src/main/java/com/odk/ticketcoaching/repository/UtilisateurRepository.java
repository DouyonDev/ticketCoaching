package com.odk.ticketcoaching.repository;

import com.odk.ticketcoaching.entity.Role;
import com.odk.ticketcoaching.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    List<Utilisateur> findByRole(Role role);
}
