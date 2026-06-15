package com.fintech.Bank.exceptions;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String error){
        super(error);
    }
}
