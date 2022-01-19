package com.scm.backend.repository;

import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.entity.Item;
import com.scm.backend.repository.custom.ItemRepositoryCustom;
import com.scm.backend.util.InternalState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom,
        PagingAndSortingRepository<Item, Long> {
    Optional<Item> findItemByItemNumber(Integer itemNumber);

    Page<Item> findByInternalStateNot(InternalState internalState, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE ITEM " +
            " SET internal_state = 'DELETED' " +
            " WHERE item_number in (:itemNumbers) ", nativeQuery = true)
    int deleteItems(@Param("itemNumbers")List<Integer> itemNumbers);
}
