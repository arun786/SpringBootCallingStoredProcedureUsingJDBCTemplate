package com.example.demo.exception;

/**
 * Created by Adwiti on 5/1/2018.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
