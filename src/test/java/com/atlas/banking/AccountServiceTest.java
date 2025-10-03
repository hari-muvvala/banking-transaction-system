package com.atlas.banking;

import com.atlas.banking.model.Account;
import com.atlas.banking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepo;
    private TransactionRepository txnRepo;
    private AccountService service;

    @BeforeEach
    void setup() {
        accountRepo = mock(AccountRepository.class);
        txnRepo = mock(TransactionRepository.class);
        service = new AccountService(accountRepo, txnRepo);
    }

    @Test
    void testDepositIncreasesBalanceAndWritesTransaction() {
        // arrange
        Account acc = new Account();
        acc.setAccountId("acc-1");
        acc.setBalance(100.0);
        when(accountRepo.get("acc-1")).thenReturn(acc);

        // act
        service.deposit("acc-1", 50.0);

        // assert balance updated
        verify(accountRepo).put(acc);
        assertEquals(150.0, acc.getBalance());

        // assert transaction written
        ArgumentCaptor<Transaction> cap = ArgumentCaptor.forClass(Transaction.class);
        verify(txnRepo).put(cap.capture());
        Transaction t = cap.getValue();
        assertEquals("DEPOSIT", t.getType());
        assertEquals(50.0, t.getAmount());
        assertEquals("acc-1", t.getAccountId());
        assertEquals("SUCCESS", t.getStatus());
    }

    @Test
    void testWithdrawDecreasesBalanceAndWritesTransaction() {
        Account acc = new Account();
        acc.setAccountId("acc-2");
        acc.setBalance(200.0);
        when(accountRepo.get("acc-2")).thenReturn(acc);

        service.withdraw("acc-2", 80.0);

        verify(accountRepo).put(acc);
        assertEquals(120.0, acc.getBalance());

        ArgumentCaptor<Transaction> cap = ArgumentCaptor.forClass(Transaction.class);
        verify(txnRepo).put(cap.capture());
        Transaction t = cap.getValue();
        assertEquals("WITHDRAW", t.getType());
        assertEquals(80.0, t.getAmount());
        assertEquals("acc-2", t.getAccountId());
        assertEquals("SUCCESS", t.getStatus());
    }

    @Test
    void testWithdrawFailsIfInsufficientFunds() {
        Account acc = new Account();
        acc.setAccountId("acc-3");
        acc.setBalance(30.0);
        when(accountRepo.get("acc-3")).thenReturn(acc);

        // expect IllegalStateException now
        assertThrows(IllegalStateException.class, () -> service.withdraw("acc-3", 50.0));

        // no put should happen
        verify(accountRepo, never()).put(any());
        verify(txnRepo, never()).put(any());
    }
}