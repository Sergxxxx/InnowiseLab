package com.epam.winter_java_lab.task_13.repos;

import com.epam.winter_java_lab.task_13.domain.Message;

import java.util.List;

public interface MessageRepositoryCustom {
    List<Message> search(String text, String tag);
}
