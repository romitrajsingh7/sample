package com.libraryManagement.repository;

import com.libraryManagement.entities.Fine;
import com.libraryManagement.enums.FineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
    List<Fine> findByMember_MemberId(Long memberId);
    List<Fine> findByStatus(FineStatus status);
    Optional<Fine> findTopByMember_MemberIdAndStatusOrderByLastPaymentDateDesc(
            Long memberId, FineStatus status
    );
    Optional<Fine> findByMember_MemberIdAndTransactionDateAndAmountAndStatus(
            Long memberId,
            LocalDate transactionDate,
            double amount,
            FineStatus status
    );
}