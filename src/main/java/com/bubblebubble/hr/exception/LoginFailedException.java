package com.bubblebubble.hr.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String message){
        super(message);
    }
}
