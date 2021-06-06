package com.epam.winter_java_lab.task_13.exception;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    private List<String> details;

}
