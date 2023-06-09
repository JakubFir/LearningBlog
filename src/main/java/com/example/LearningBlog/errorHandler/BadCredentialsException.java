package com.example.LearningBlog.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String msg){
        super(msg);
    }
}
