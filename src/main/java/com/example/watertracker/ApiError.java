package com.example.watertracker;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/*
 * Object to help with error handling
 */
public class ApiError {

    // It stores the HTTP return status
    private HttpStatus status;

    // An error message to display
    private String message;

    // List of errors, that occurred
    private List<String> errors;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
