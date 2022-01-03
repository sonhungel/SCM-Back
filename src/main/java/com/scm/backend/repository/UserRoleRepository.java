package com.scm.backend.repository;

import com.scm.backend.model.entity.Role;
import com.scm.backend.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole> {
    List<UserRole> findByKey_User_Id(Long userId);
}
