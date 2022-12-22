package de.othr.im.gymbro.rest;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException() {
        super("You are not logged in");
    }
}