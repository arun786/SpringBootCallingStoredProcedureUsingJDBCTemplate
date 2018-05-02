package com.example.demo.exception;

import com.example.demo.model.CountryConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Adwiti on 5/1/2018.
 */
@ControllerAdvice
@RestController
public class GenericException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleDataNotFoundException(DataNotFoundException e, WebRequest w) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CountryConstants.DATA_NOT_FOUND, e.getMessage());
        ResponseEntity<ExceptionResponse> response = new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
        return response;
    }
}
