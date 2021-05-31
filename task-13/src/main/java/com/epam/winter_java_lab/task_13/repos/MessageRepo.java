package com.epam.winter_java_lab.task_13.repos;

import com.epam.winter_java_lab.task_13.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface MessageRepo extends CrudRepository<Message, Long> {
    Stream<Message> findByTag(String tag, Pageable pageable);

    @Query(value = "select * from (select *, row_number() over(partition " +
            "by user_id order by created_date_time desc) " +
            "as rn from message)t " +
            "where rn=1 order by created_date_time desc limit 10;", nativeQuery = true)
    Stream<Message> getMessagesByCreatedDateTime();

    Page<Message> findAll(Pageable pageable);
}
