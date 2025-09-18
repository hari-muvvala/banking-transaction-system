package com.atlas.banking;

import com.atlas.banking.model.Account;

public class AccountService {
    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public void deposit(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        Account account = repo.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        account.setBalance(account.getBalance() + amount);
        repo.put(account);
    }

    public void withdraw(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive.");
        }
        Account account = repo.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        account.setBalance(account.getBalance() - amount);
        repo.put(account);
    }
}