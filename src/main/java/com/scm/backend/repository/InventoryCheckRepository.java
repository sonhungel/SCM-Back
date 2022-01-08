package com.scm.backend.repository;

import com.scm.backend.model.entity.InventoryCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface InventoryCheckRepository extends JpaRepository<InventoryCheck, Long>, QuerydslPredicateExecutor<InventoryCheck> {
}
