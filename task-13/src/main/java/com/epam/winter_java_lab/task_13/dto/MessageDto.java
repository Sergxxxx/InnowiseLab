package com.epam.winter_java_lab.task_13.dto;

import com.epam.winter_java_lab.task_13.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;

    private String text;

    private String tag;

    private LocalDateTime createdDateTime;

    private LocalDateTime updatedDateTime;

    private User author;

}
