package com.scm.backend.repository;

import com.scm.backend.model.entity.User;
import com.scm.backend.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, UserRepositoryCustom {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(Long id);
}
