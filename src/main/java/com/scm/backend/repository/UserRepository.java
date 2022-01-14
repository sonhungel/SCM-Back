package com.scm.backend.repository;

import com.scm.backend.model.dto.DailyReportDto;
import com.scm.backend.model.entity.User;
import com.scm.backend.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, UserRepositoryCustom {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(Long id);


    @Query(value = "select sum(paid) from invoice where added_date = :date and status = 'CLOSED';", nativeQuery = true)
    List<DailyReportDto> getDailyPaid(@Param("date") LocalDate date);

    @Query(value = "select sum(paid) from invoice where added_date = :date and status = 'CLOSED';", nativeQuery = true)
    List<DailyReportDto> getDailyCost(@Param("date") LocalDate date);

    @Query(value = "select max(id) from item", nativeQuery = true)
    int getLatestItemId();

    @Query(value = "select max(id) from customer", nativeQuery = true)
    int getLatestCustomerId();

    @Query(value = "select max(id) from supplier", nativeQuery = true)
    int getLatestSupplierId();


    @Modifying
    @Query(value = "UPDATE ITEM " +
            " SET internal_state = 'DELETED' " +
            " WHERE item_number in (:itemNumbers) ", nativeQuery = true)
    int deleteItems(@Param("itemNumbers") List<Integer> itemNumbers);
}
