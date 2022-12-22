package de.othr.im.gymbro.rest;

public class InvalidAccessException extends RuntimeException {
    public InvalidAccessException() {
        super("You are not allowed to access this resource");
    }
}