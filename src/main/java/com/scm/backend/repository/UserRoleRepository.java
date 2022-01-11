package com.scm.backend.repository;

import com.scm.backend.model.entity.Role;
import com.scm.backend.model.entity.UserRole;
import com.scm.backend.model.entity.UserRoleKey;
import com.scm.backend.repository.custom.UserRoleCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole>, UserRoleCustomRepository {
    List<UserRole> findByKey_User_Id(Long userId);
    @Modifying
    @Query(value = "DELETE FROM user_role WHERE user_id = ?1", nativeQuery = true)
    int deleteByUserIdRoleId(Long userId);
}
