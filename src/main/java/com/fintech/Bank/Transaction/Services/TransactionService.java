package com.fintech.Bank.Transaction.Services;

import com.fintech.Bank.Transaction.dtos.TransactionDTO;
import com.fintech.Bank.Transaction.dtos.TransactionRequest;
import com.fintech.Bank.res.Response;

import java.util.List;

public interface TransactionService {
//the arguments in this it takes this as input
    Response<?>createTransaction(TransactionRequest transactionRequest); //Create a new transaction
    Response<List<TransactionDTO>>getTransactionsForMyAccount(String accountNumber,int page,int size);
 //<Response<T> is the wrapper around the API response where T is the type of data being returned


}
