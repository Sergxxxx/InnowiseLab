package com.innowise.task.dto;

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

    private Long userId;

}