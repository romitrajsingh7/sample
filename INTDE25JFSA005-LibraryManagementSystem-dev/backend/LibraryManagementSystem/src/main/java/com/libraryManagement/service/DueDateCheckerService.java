package com.libraryManagement.service;

import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.enums.TransactionStatus;
import com.libraryManagement.repository.BorrowingTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DueDateCheckerService {
    private static final Logger logger = LoggerFactory.getLogger(DueDateCheckerService.class);

    @Autowired
    private BorrowingTransactionRepository transactionRepo;

    @Autowired
    private NotificationService notificationService;

    public void checkDueDates() {
        logger.info("Checking due dates...");

        List<BorrowingTransaction> borrowed = transactionRepo
                .findByStatusAndReturnDateIsNullAndBorrowDateBefore(
                        TransactionStatus.BORROWED,
                        LocalDate.now().minusDays(14)
                );

        for (BorrowingTransaction tx : borrowed) {
            Long memberId = tx.getMember().getMemberId();
            int bookId = tx.getBook().getBookId();
            String title = tx.getBook().getTitle();
            long days = ChronoUnit.DAYS.between(tx.getBorrowDate(), LocalDate.now());

            if (days >= 12 && days < 14) {
                notificationService.createNotification(
                        memberId,
                        bookId,
                        title,
                        "Your book is due in 2 days."
                );
            } else if (days >= 14) {
                notificationService.createNotification(
                        memberId,
                        bookId,
                        title,
                        "Your book is overdue. Please return it or pay the overdue fine."
                );
            }
        }

        logger.info("Due date check complete.");
    }
}
