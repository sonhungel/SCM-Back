package com.scm.backend.repository;

import com.scm.backend.model.entity.SupTicket;
import com.scm.backend.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupTicketRepository extends JpaRepository<SupTicket, Long>, QuerydslPredicateExecutor<SupTicket> {
}
