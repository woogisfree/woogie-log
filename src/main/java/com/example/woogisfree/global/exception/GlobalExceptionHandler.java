package com.example.woogisfree.global.exception;

import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.user.exception.EmailAlreadyExistsException;
import com.example.woogisfree.domain.user.exception.PasswordMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 로그인, 회원가입
     */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage())
                        .build());
    }

    /**
     * 게시글
     */
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundException(ArticleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value()).message(e.getMessage())
                        .build());
    }
}