package com.scm.backend.repository;

import com.scm.backend.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, QuerydslPredicateExecutor<Customer> {
    Optional<Customer> findCustomerByCustomerNumber(Integer customerNumber);
}
