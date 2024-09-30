package com.kb.zipkim.domain.transaction.repository;

import com.kb.zipkim.domain.transaction.TransactionType;
import com.kb.zipkim.domain.transaction.entity.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    @Query("""
           SELECT t from TransactionHistory t
           where t.area.id = :areaId
           and t.type = :type
           order by t.tradeYear desc, t.tradeMonth desc
           """)
    Page<TransactionHistory> findByAreaIdAndType(@Param("areaId") Long area_id,@Param("type") TransactionType type, Pageable pageable);
}
