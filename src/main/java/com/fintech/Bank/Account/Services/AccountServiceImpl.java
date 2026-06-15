package com.fintech.Bank.Account.Services;

import com.fintech.Bank.Account.Repo.AccountRepo;
import com.fintech.Bank.Account.dtos.AccountDTO;
import com.fintech.Bank.Account.entity.Account;
import com.fintech.Bank.Auth_User.Services.UserService;
import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.enums.AccountStatus;
import com.fintech.Bank.enums.AccountType;
import com.fintech.Bank.enums.Currency;
import com.fintech.Bank.exceptions.BadRequestException;
import com.fintech.Bank.exceptions.NotFoundException;
import com.fintech.Bank.res.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
private final AccountRepo accountRepo;
private final UserService userService;
private final ModelMapper modelMapper;
private final Random random = new Random();


    @Override
    public Account createAccount(AccountType accountType, User user) {
        log.info("Inside createAccount()");
        String accountNumber = generateAccountNumber();
        Account account = Account.builder().accountNumber(accountNumber).accountType(accountType)
                .currency(Currency.USD)
                .balance(BigDecimal.ZERO)
                .accountStatus(AccountStatus.ACTIVE)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        return accountRepo.save(account);
    }

    @Override
    public Response<List<AccountDTO>> getMyAccounts() {
        User user = userService.getCurrentLoggedInUser();

        List<AccountDTO>accounts= accountRepo.findByUserId(user.getId()).stream().map(account->modelMapper.map(account,AccountDTO.class)).toList();

        return Response.<List<AccountDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User Accounts fetched successfully")
                .data(accounts)
                .build();
    }

    @Override
    public Response<?> closeAccount(String accountNumber) {
        User user = userService.getCurrentLoggedInUser();
        Account account =  accountRepo.findByAccountNumber(accountNumber).orElseThrow(()->new NotFoundException("No Account Found"));

        if(!user.getAccounts().contains(account)){
            throw new NotFoundException("Account doesnt belong to you");

        }

        if(account.getBalance().compareTo(BigDecimal.ZERO)>0){
            throw new BadRequestException("Account Balance must be zero before closing");
        }

        account.setAccountStatus(AccountStatus.CLOSED);
        account.setClosedAt(LocalDateTime.now());
        accountRepo.save(account);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User Accounts fetched successfully")
                .build();

    }

    private String generateAccountNumber(){
        String accountNumber;

        do{
           accountNumber = "66"+(random.nextInt(90000000)+10000000);
        }while(accountRepo.findByAccountNumber(accountNumber).isPresent());

        log.info("account number generated {}",accountRepo);
        return accountNumber;
    }
}
