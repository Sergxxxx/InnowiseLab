package com.innowise.task.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder(setterPrefix = "with")
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Please write a note")
    @Length(max = 1000, message = "Note too long")
    @Column(name = "text", nullable = false)
    private String text;

    @NotBlank(message = "Please write a tag")
    @Length(max = 30, message = "Tag too long")
    @Column(name = "tag", nullable = false)
    private String tag;

    @CreatedDate
    @Column (name = "created_date_time", nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column (name = "updated_date_time", nullable = false)
    private LocalDateTime updatedDateTime;

    @JoinColumn(name = "user_id")
    private Long userId;

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }

}