package com.bubblebubble.hr.login.exception;

public class DuplicatedMemberEmailException extends RuntimeException{

    public DuplicatedMemberEmailException(String message){
        super(message);
    }
}
