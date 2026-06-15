package com.fintech.Bank.Account.Services;

import com.fintech.Bank.Account.dtos.AccountDTO;
import com.fintech.Bank.Account.entity.Account;
import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.enums.AccountType;
import com.fintech.Bank.res.Response;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountType accountType, User user);

    Response<List<AccountDTO>>getMyAccounts();

    Response<?>closeAccount(String accountNumber);
}
