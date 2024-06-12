package com.odk.ticketcoaching.repository;

import com.odk.ticketcoaching.entity.Ticket;
import com.odk.ticketcoaching.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
