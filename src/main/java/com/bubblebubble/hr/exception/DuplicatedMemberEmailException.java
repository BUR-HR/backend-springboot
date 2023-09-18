package com.bubblebubble.hr.exception;

public class DuplicatedMemberEmailException extends RuntimeException{

    public DuplicatedMemberEmailException(String message){
        super(message);
    }
}
