package com.innowise.task.repository;

import com.innowise.task.domain.Message;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.tag like %:tag%")
    Page<Message> findByTagLike(String tag, Pageable pageable);

    @Query("select m from Message m " +
        "where m.userId in (select distinct m.userId from Message m) " +
        "order by m.createdDateTime desc")
    Page<Message> findMessageTop(Pageable pageable);

    @NonNull
    Page<Message> findAll(@NonNull Pageable pageable);

    Page<Message> findMessagesByUserId(Long userId, Pageable pageable);

    Optional<Message> findMessageById(Long messageId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "call rollback_message(:desired_date);", nativeQuery = true)
    void rollbackMessage(LocalDateTime desired_date);

}