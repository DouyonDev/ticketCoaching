package com.odk.ticketcoaching.repository;

import com.odk.ticketcoaching.entity.BaseConnaissance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseConnaissanceRepository extends JpaRepository<BaseConnaissance, Integer> {
}
