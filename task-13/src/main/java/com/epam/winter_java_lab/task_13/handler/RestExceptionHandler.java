package com.epam.winter_java_lab.task_13.handler;

<<<<<<< HEAD
import com.epam.winter_java_lab.task_13.exception.MessageNotFoundException;
=======
import com.epam.winter_java_lab.task_13.exception.ErrorResponse;
import com.epam.winter_java_lab.task_13.exception.NotFoundException;
import org.springframework.http.HttpStatus;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

<<<<<<< HEAD
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
=======
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
private static final String INCORRECT_REQUEST = "INCORRECT_REQUEST";

>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(final IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

<<<<<<< HEAD
    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleBadRequest(final MessageNotFoundException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
=======
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(NotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

}
