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


    @Query(value = "select sum(paid) as paid from invoice where added_date = :date and status = 'CLOSED';", nativeQuery = true)
    List<DailyReportDto> getDailyPaid(@Param("date") LocalDate date);

    @Query(value = "select sum(cost) as cost from sup_ticket where added_date = :date ;", nativeQuery = true)
    List<DailyReportDto> getDailyCost(@Param("date") LocalDate date);

    @Query(value = "select sum(paid) as paid, added_date as date from invoice where added_date between :fromDate and :toDate " +
            " group by added_date;", nativeQuery = true)
    List<DailyReportDto> getWeeklyPaidReport(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query(value = "select sum(cost) as cost, added_date as date from sup_ticket where added_date between :fromDate and :toDate " +
            " group by added_date;", nativeQuery = true)
    List<DailyReportDto> getWeeklyCostReport(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query(value = "select sum(cost) as cost from sup_ticket where added_date between :fromDate and :toDate " +
            " ;", nativeQuery = true)
    List<DailyReportDto> getMonthlyCostReport(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query(value = "select sum(paid) as paid from invoice where added_date between :fromDate and :toDate " +
            " ;", nativeQuery = true)
    List<DailyReportDto> getMonthlyPaidReport(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query(value = "select sum(paid) as paid, added_date as date from invoice where added_date between :fromDate and :toDate " +
            " group by added_date;", nativeQuery = true)
    List<DailyReportDto> getPaidReport(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query(value = "select sum(cost) as cost, added_date as date from sup_ticket where added_date between :fromDate and :toDate " +
            " group by added_date;", nativeQuery = true)
    List<DailyReportDto> getCostReport(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query(value = "select max(item_number) from item", nativeQuery = true)
    int getLatestItemNumber();

    @Query(value = "select max(customer_number) from customer", nativeQuery = true)
    int getLatestCustomerNumber();

    @Query(value = "select max(supplier_number) from supplier", nativeQuery = true)
    int getLatestSupplierNumber();


    @Modifying
    @Query(value = "UPDATE ITEM " +
            " SET internal_state = 'DELETED' " +
            " WHERE item_number in (:itemNumbers) ", nativeQuery = true)
    int deleteItems(@Param("itemNumbers") List<Integer> itemNumbers);
}
