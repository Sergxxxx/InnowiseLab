package com.epam.winter_java_lab.task_13.domain;

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
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotBlank(message = "Please write a note")
    @Length(max = 1000, message = "Note too long")
    @Column(name = "text", nullable = false)
    private String text;

    @Getter
    @Setter
    @NotBlank(message = "Please write a tag")
    @Length(max = 30, message = "Tag too long")
    @Column(name = "tag", nullable = false)
    private String tag;

    @Getter
    @CreatedDate
    @Column (name = "created_date_time", nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @Getter
    @LastModifiedDate
    @Column (name = "updated_date_time", nullable = false)
    private LocalDateTime updatedDateTime;

    @Getter
    @Setter
    @JoinColumn(name = "user_id")
    private Long userId;

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }

}









