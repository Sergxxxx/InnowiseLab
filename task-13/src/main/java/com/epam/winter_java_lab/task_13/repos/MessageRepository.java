package com.epam.winter_java_lab.task_13.repos;

import com.epam.winter_java_lab.task_13.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long>,
                                     JpaSpecificationExecutor<Message>,
                                      MessageRepositoryCustom{

    Page<Message> findByTag(String tag, Pageable pageable);

    Page<Message> findTop10ByOrderByCreatedDateTimeDesc(Pageable pageable);

    Page<Message> findAll(Pageable pageable);

    Page<Message> findMessagesByUserId(Long userId, Pageable pageable);

    Optional<Message> findMessageById(Long messageId);

}
