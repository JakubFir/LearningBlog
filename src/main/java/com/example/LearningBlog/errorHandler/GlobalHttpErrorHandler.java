package com.example.LearningBlog.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException exception) {
        return new ResponseEntity<>("post with given Id dosnt exists", HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("user with given Id dosnt exists", HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<Object> handleUsernameTakenException(UsernameTakenException exception){
        return  new ResponseEntity<>("user with given username is taken", HttpStatus.BAD_REQUEST);
    }

}

