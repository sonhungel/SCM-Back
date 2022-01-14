package com.scm.backend.repository;

import com.scm.backend.model.entity.Supplier;
import com.scm.backend.util.InternalState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, QuerydslPredicateExecutor<Supplier> {
    Optional<Supplier> findSupplierBySupplierNumber(Integer supplierNumber);

    @Query("select s from #{#entityName} s where internal_state != 'DELETED'")
    List<Supplier> getAllActiveSupplier();
}
