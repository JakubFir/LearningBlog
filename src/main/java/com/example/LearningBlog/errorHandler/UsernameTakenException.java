package com.example.LearningBlog.errorHandler;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class UsernameTakenException extends RuntimeException {

    public UsernameTakenException(String msg){
        super(msg);
    }

}
