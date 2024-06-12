package com.odk.ticketcoaching.repository;

import com.odk.ticketcoaching.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Utilisateur, Integer> {

}
