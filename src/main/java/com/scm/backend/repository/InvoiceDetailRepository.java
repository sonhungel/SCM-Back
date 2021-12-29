package com.scm.backend.repository;

import com.scm.backend.model.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long>, QuerydslPredicateExecutor<InvoiceDetail> {
}
