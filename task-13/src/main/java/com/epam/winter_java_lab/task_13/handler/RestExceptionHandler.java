package com.epam.winter_java_lab.task_13.handler;

import com.epam.winter_java_lab.task_13.exception.MessageNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(final IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleBadRequest(final MessageNotFoundException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
