package com.example.wikiservice.exception.handler;

import com.example.wikiservice.exception.ArticleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException exception: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleError(Exception ex) {
        log.error("RuntimeException exception: " + ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<Void> handleArticleNotFoundException(ArticleNotFoundException ex) {
        log.error("ArticleNotFoundException exception: " + ex.getMessage() + " article not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}