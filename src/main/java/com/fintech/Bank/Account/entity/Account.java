package com.fintech.Bank.Account.entity;

import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.enums.AccountStatus;
import com.fintech.Bank.enums.AccountType;
import com.fintech.Bank.enums.Currency;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import com.fintech.Bank.Transaction.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 15)
    private String accountNumber;

    @Column(nullable = false, precision = 19,scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;


    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();


    private LocalDateTime closedAt;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;





}
