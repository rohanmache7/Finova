
package com.fintech.Bank.exceptions;

public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException(String error){
        super(error);
    }

}
