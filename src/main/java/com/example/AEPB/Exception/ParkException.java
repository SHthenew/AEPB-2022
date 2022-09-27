package com.example.AEPB.Exception;

public abstract class ParkException extends RuntimeException {
    public ParkException(String message) {
        super(message);
    }
}
