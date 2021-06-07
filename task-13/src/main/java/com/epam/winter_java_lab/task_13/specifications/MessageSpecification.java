package com.epam.winter_java_lab.task_13.specifications;

import com.epam.winter_java_lab.task_13.domain.Message;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MessageSpecification {
    public static Specification<Message> getMessagesByText(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("text"), text);
    }

    public static Specification<Message> getMessagesByTag(String tag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tag"), tag);
    }

}
