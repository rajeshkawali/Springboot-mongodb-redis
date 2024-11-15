package com.rajeshkawali.exception;
/**
 * @author Rajesh_Kawali
 */
public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}