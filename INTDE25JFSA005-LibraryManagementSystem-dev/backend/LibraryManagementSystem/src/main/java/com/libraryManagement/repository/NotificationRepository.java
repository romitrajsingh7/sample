package com.libraryManagement.repository;

import com.libraryManagement.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByMember_MemberId(Long memberId);

    Optional<Notification> findByMember_MemberIdAndDateSentAndMessageContaining(
            Long memberId,
            LocalDate dateSent,
            String messagePart
    );

    void deleteByMember_MemberIdAndMessageContaining(
            Long memberId,
            String messagePart
    );
}
