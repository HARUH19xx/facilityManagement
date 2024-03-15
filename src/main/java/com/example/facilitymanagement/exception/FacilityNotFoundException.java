package com.example.facilitymanagement.exception;

public class FacilityNotFoundException extends RuntimeException {
    public FacilityNotFoundException(String message) {
        super(message);
    }
}
