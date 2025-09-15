package org.example.study_group_service.exceptions;

import org.example.study_group_service.models.dto.outcomming.ErrorMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.badRequest().body( new ErrorMessageDTO("Bad Request: " + e.getMessage()) );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleOtherException(RuntimeException e){
        return ResponseEntity.internalServerError().body( new ErrorMessageDTO("Internal Server Error") );
    }
}

