package com.smartcode.SpringMVC.exceptions;

public class VerificationException extends RuntimeException{
    public VerificationException(String message){
        super(message);
    }
}
