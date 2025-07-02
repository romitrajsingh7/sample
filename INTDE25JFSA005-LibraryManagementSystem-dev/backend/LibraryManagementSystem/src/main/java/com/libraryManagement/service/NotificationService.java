package com.libraryManagement.service;

import com.libraryManagement.entities.Member;
import com.libraryManagement.entities.Notification;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberRepository memberRepository;


    public void createNotification(Long memberId, int bookId, String bookTitle, String message) {
        String baseMessage = "Book ID: " + bookId;
        LocalDate today = LocalDate.now();

        logger.info("Checking existing notification for memberId={}, date={}, bookId={}", memberId, today, bookId);

        Optional<Notification> existing = notificationRepository
                .findByMember_MemberIdAndDateSentAndMessageContaining(
                        memberId,
                        today,
                        baseMessage
                );

        if (existing.isPresent()) {
            logger.info("Notification already exists for bookId={}, skipping.", bookId);
            return;
        }

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found: " + memberId));

        Notification notification = new Notification();
        notification.setMember(member);
        notification.setMessage(baseMessage + " (" + bookTitle + ") - " + message);
        notification.setDateSent(today);

        notificationRepository.save(notification);
        logger.info("Created notification for memberId={}, bookId={}", memberId, bookId);
    }

    public void removeNotificationsForReturnedBook(Long memberId, int bookId) {
        String baseMessage = "Book ID: " + bookId;
        logger.info("Removing notifications for returned bookId={} of memberId={}", bookId, memberId);
        notificationRepository.deleteByMember_MemberIdAndMessageContaining(memberId, baseMessage);
        logger.info("Notifications removed for bookId={}.", bookId);
    }
}
