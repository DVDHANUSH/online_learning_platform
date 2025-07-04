package com.service.category.Exceptions;
import com.service.category.dto.CustomMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotPresentException.class)
    public  ResponseEntity<CustomMessage> handleResourceNotPresent(ResourceNotPresentException ex){
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage(ex.getMessage());
        customMessage.setSuccess(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();


            errors.put(fieldName, errorMessage);
        });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<CustomMessage> handleAuthDeniedException(AuthorizationDeniedException ex) {
//        System.out.println("I am inside the exception handler");
//        CustomMessage customMessage = new CustomMessage();
//        customMessage.setSuccess(false);
//        customMessage.setMessage(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customMessage);
//    }
}