package com.epam.winter_java_lab.task_13.repos;

import com.epam.winter_java_lab.task_13.domain.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom{

    private EntityManager em;

    public MessageRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Message> search(String text, String tag) {
        // create message query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
        Root<Message> messageRoot = cq.from(Message.class);

        Predicate textPredicate = cb.like(messageRoot.get("text"), "%" + text + "%");

        Predicate tagPredicate = cb.like(messageRoot.get("tag"), "%" + tag + "%");

        cq.where(textPredicate, tagPredicate);

        TypedQuery<Message> query = em.createQuery(cq);

        return query.getResultList();
    }

}
