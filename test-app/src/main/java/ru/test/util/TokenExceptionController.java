package ru.test.util;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionController {

    @ExceptionHandler(JWTCreationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseErrorDto jwtCreationException(JWTCreationException e) {
        return new ResponseErrorDto("Token creation exception");
    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseErrorDto jwtVerificationException(JWTVerificationException e) {
        return new ResponseErrorDto("Token verification exception");
    }
}

class ResponseErrorDto {
    private String message;

    public ResponseErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
