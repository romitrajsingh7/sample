package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.FinePaymentRequestDto;
import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.entities.Fine;
import com.libraryManagement.enums.FineStatus;
import com.libraryManagement.enums.TransactionStatus;
import com.libraryManagement.exceptions.FinePaymentException;
import com.libraryManagement.repository.BorrowingTransactionRepository;
import com.libraryManagement.repository.FineRepository;
import com.libraryManagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class FineServiceImpl implements FineService {

    private static final double FINE_PER_DAY = 0.50;

    @Autowired
    private FineRepository fineRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BorrowingTransactionRepository transactionRepo;

    @Override
    public List<Fine> getMemberFines(Long memberId) {
        return fineRepo.findByMember_MemberId(memberId);
    }

    @Override
    public List<Fine> getPendingFines() {
        return fineRepo.findByStatus(FineStatus.PENDING);
    }

    @Override
    @Transactional
    public Fine payFine(FinePaymentRequestDto request) {
        Fine fine = fineRepo.findById(request.getFineId())
                .orElseThrow(() -> new FinePaymentException("Invalid fine ID"));

        if (fine.getStatus() == FineStatus.PAID) {
            throw new FinePaymentException("Fine is already paid");
        }

        fine.setStatus(FineStatus.PAID);
        fine.setLastPaymentDate(LocalDate.now());
        return fineRepo.save(fine);
    }

    @Override
    @Transactional
    public void calculateOverdueFines() {
        List<BorrowingTransaction> overdueTxs = transactionRepo
                .findByStatusAndReturnDateIsNullAndBorrowDateBefore(
                        TransactionStatus.BORROWED,
                        LocalDate.now().minusDays(14)
                );

        for (BorrowingTransaction tx : overdueTxs) {
            Long memberId = tx.getMember().getMemberId();

            LocalDate startDate = fineRepo
                    .findTopByMember_MemberIdAndStatusOrderByLastPaymentDateDesc(
                            memberId, FineStatus.PAID
                    )
                    .filter(f -> f.getLastPaymentDate() != null)
                    .map(Fine::getLastPaymentDate)
                    .orElse(tx.getBorrowDate().plusDays(14));

            long overdueDays = ChronoUnit.DAYS.between(startDate, LocalDate.now());
            if (overdueDays <= 0) continue;

            double amount = overdueDays * FINE_PER_DAY;
            LocalDate today  = LocalDate.now();

            boolean duplicate = fineRepo
                    .findByMember_MemberIdAndTransactionDateAndAmountAndStatus(
                            memberId, today, amount, FineStatus.PENDING
                    )
                    .isPresent();

            if (duplicate) {
                continue;
            }
            Fine f = new Fine();
            f.setMember(tx.getMember());
            f.setAmount(amount);
            f.setStatus(FineStatus.PENDING);
            f.setTransactionDate(today);
            fineRepo.save(f);
        }
    }

}
