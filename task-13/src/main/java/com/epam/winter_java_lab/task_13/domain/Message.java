package com.epam.winter_java_lab.task_13.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public void setUpdatedDateTime(Long updatedDateTime) {
        this.updatedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedDateTime), ZoneId.systemDefault());
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(createdDateTime), ZoneId.systemDefault());
    }
}









