package com.fintech.Bank.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String error){
        super(error);
    }
}
