package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.BorrowRequestDto;
import com.libraryManagement.dto.requestDto.ReturnRequestDto;
import com.libraryManagement.dto.responseDto.BorrowingResponseDto;
import com.libraryManagement.entities.Book;
import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.entities.Member;
import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.enums.TransactionStatus;
import com.libraryManagement.exceptions.BookNotAvailableException;
import com.libraryManagement.exceptions.BorrowingLimitExceededException;
import com.libraryManagement.exceptions.InvalidTransactionException;
import com.libraryManagement.repository.BookRepository;
import com.libraryManagement.repository.BorrowingTransactionRepository;
import com.libraryManagement.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    private static final Logger logger = LoggerFactory.getLogger(BorrowingServiceImpl.class);

    private static final int MAX_BORROW_LIMIT = 5;
    private static final int BORROWING_PERIOD_DAYS = 14;

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BorrowingTransactionRepository transactionRepo;
    @Override
    @Transactional
    public BorrowingResponseDto borrowBook(BorrowRequestDto request) {
        Member member = memberRepo.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        if (member.getMembershipStatus() != MembershipStatus.ACTIVE) {
            throw new IllegalStateException("Member is not active and cannot borrow books");
        }

        Book book = bookRepo.findById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        // ✅ Prevent borrowing multiple copies of same book
        boolean hasAlreadyBorrowed = transactionRepo
                .existsByMember_MemberIdAndBook_BookIdAndStatus(request.getMemberId(), request.getBookId(), TransactionStatus.BORROWED);
        if (hasAlreadyBorrowed) {
            throw new IllegalStateException("Member has already borrowed this book and has not returned it yet");
        }

        if (!isBookAvailable(book.getBookId())) {
            throw new BookNotAvailableException("Book with ID " + book.getBookId() + " is not available");
        }

        if (getCurrentBorrowCount(member.getMemberId()) >= MAX_BORROW_LIMIT) {
            throw new BorrowingLimitExceededException("Member has reached the maximum borrow limit");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        BorrowingTransaction tx = new BorrowingTransaction();
        tx.setBook(book);
        tx.setMember(member);
        tx.setBorrowDate(LocalDate.now());
        tx.setStatus(TransactionStatus.BORROWED);
        tx = transactionRepo.save(tx);

        return new BorrowingResponseDto(tx.getTransactionID(), "Book borrowed successfully");
    }


    @Override
    @Transactional
    public BorrowingResponseDto returnBook(ReturnRequestDto request) {
        BorrowingTransaction tx = transactionRepo.findById(request.getTransactionId())
                .orElseThrow(() -> new InvalidTransactionException("Invalid transaction ID"));

        if (tx.getStatus() != TransactionStatus.BORROWED) {
            throw new InvalidTransactionException("Transaction is not in BORROWED status");
        }

        tx.setReturnDate(LocalDate.now());
        tx.setStatus(TransactionStatus.RETURNED);
        transactionRepo.save(tx);

        Book book = tx.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        // **Delete notifications related to this book for the user**
        try {
            logger.info("Deleting Notification");
            notificationService.removeNotificationsForReturnedBook(
                    tx.getMember().getMemberId(),
                    tx.getBook().getBookId()
            );
        } catch (Exception ex) {
            logger.warn("Could not clear notifications for return transaction {}: {}",
                    tx.getTransactionID(), ex.getMessage());
        }
        logger.info("Book Returned");
        return new BorrowingResponseDto(tx.getTransactionID(), "Book returned successfully");
    }


    @Override
    @Transactional
    public List<BorrowingTransaction> getMemberTransactions(Long memberId) {
        return transactionRepo.findByMember_MemberId(memberId);
    }

    @Override
    @Transactional
    public List<BorrowingTransaction> getOverdueTransactions() {
        LocalDate dueDate = LocalDate.now().minusDays(BORROWING_PERIOD_DAYS);
        return transactionRepo.findByStatusAndBorrowDateBefore(TransactionStatus.BORROWED, dueDate);
    }
    @Override
    @Transactional
    public List<BorrowingTransaction> getAllTransactions(){
        return transactionRepo.findAll();
    }

    private boolean isBookAvailable(Integer bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"))
                .getAvailableCopies() > 0;
    }

    private long getCurrentBorrowCount(Long memberId) {
        return transactionRepo.countByMember_MemberIdAndStatus(memberId, TransactionStatus.BORROWED);
    }
}