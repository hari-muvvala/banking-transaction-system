package com.atlas.banking;

import com.atlas.banking.model.Account;
import com.atlas.banking.model.Transaction;

import java.time.Instant;
import java.util.UUID;

public class AccountService {
    private final AccountRepository accountRepo;
    private final TransactionRepository txnRepo;

    public AccountService(AccountRepository accountRepo, TransactionRepository txnRepo) {
        this.accountRepo = accountRepo;
        this.txnRepo = txnRepo;
    }

    // ------- Deposit -------
    public void deposit(String accountId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");

        Account acc = accountRepo.get(accountId);
        if (acc == null) throw new IllegalArgumentException("Account not found: " + accountId);

        acc.setBalance(acc.getBalance() + amount);
        accountRepo.put(acc);

        Transaction t = new Transaction();
        t.setTransactionId(UUID.randomUUID().toString());
        t.setAccountId(accountId);
        t.setType("DEPOSIT");
        t.setAmount(amount);
        t.setStatus("SUCCESS");
        t.setTimestamp(Instant.now().toString());
        txnRepo.put(t);
    }

    // ------- Withdraw -------
    public void withdraw(String accountId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw must be positive");

        Account acc = accountRepo.get(accountId);
        if (acc == null) throw new IllegalArgumentException("Account not found: " + accountId);
        if (acc.getBalance() < amount) throw new IllegalArgumentException("Insufficient funds");

        acc.setBalance(acc.getBalance() - amount);
        accountRepo.put(acc);

        Transaction t = new Transaction();
        t.setTransactionId(UUID.randomUUID().toString());
        t.setAccountId(accountId);
        t.setType("WITHDRAW");
        t.setAmount(amount);
        t.setStatus("SUCCESS");
        t.setTimestamp(Instant.now().toString());
        txnRepo.put(t);
    }
}