package org.example.study_group_service.exceptions;

import org.example.study_group_service.models.dto.outcomming.ErrorMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.badRequest().body( new ErrorMessageDTO("Bad Request: " + e.getMessage()) );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = "Validation failed: " + e.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body( new ErrorMessageDTO(errorMessage) );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body( new ErrorMessageDTO("Bad Request: " + e.getMessage()) );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleAuthenticationFailureException(AuthenticationFailureException e){
        return ResponseEntity.status(401).body(new ErrorMessageDTO(e.getMessage()));
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleBadCredentialsException(BadCredentialsException e){
        return ResponseEntity.status(401).body( new ErrorMessageDTO("Bad credentials") );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleOtherException(RuntimeException e){
        return ResponseEntity.internalServerError().body( new ErrorMessageDTO("Internal Server Error " + e) );
    }
}

