package com.atlas.banking;

import com.atlas.banking.model.Account;
import com.atlas.banking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountService service;
    private AccountRepository mockRepo;
    private TransactionRepository mockTxnRepo;

    @BeforeEach
    void setUp() {
        mockRepo = mock(AccountRepository.class);
        mockTxnRepo = mock(TransactionRepository.class);
        service = new AccountService(mockRepo, mockTxnRepo);
    }

    @Test
    void deposit_increases_balance_and_writes_txn() {
        // Arrange
        Account acc = new Account();
        acc.setAccountId("acc-1002");
        acc.setBalance(500.0);
        when(mockRepo.get("acc-1002")).thenReturn(acc);

        // Act
        service.deposit("acc-1002", 200.0);

        // Assert: balance changed in memory
        assertEquals(700.0, acc.getBalance(), 0.0001);

        // Assert: repo put was called with updated account
        verify(mockRepo).put(acc);

        // Assert: a Transaction was written with correct fields
        ArgumentCaptor<Transaction> txCap = ArgumentCaptor.forClass(Transaction.class);
        verify(mockTxnRepo).put(txCap.capture());
        Transaction t = txCap.getValue();
        assertEquals("acc-1002", t.getAccountId());
        assertEquals("DEPOSIT", t.getType());
        assertEquals(200.0, t.getAmount(), 0.0001);
        assertEquals("SUCCESS", t.getStatus());
    }

    @Test
    void withdraw_decreases_balance_and_writes_txn() {
        Account acc = new Account();
        acc.setAccountId("acc-1002");
        acc.setBalance(500.0);
        when(mockRepo.get("acc-1002")).thenReturn(acc);

        service.withdraw("acc-1002", 100.0);

        assertEquals(400.0, acc.getBalance(), 0.0001);
        verify(mockRepo).put(acc);

        ArgumentCaptor<Transaction> txCap = ArgumentCaptor.forClass(Transaction.class);
        verify(mockTxnRepo).put(txCap.capture());
        assertEquals("WITHDRAW", txCap.getValue().getType());
        assertEquals(100.0, txCap.getValue().getAmount(), 0.0001);
        assertEquals("SUCCESS", txCap.getValue().getStatus());
    }

    @Test
    void withdraw_throws_for_insufficient_funds() {
        Account acc = new Account();
        acc.setAccountId("acc-1002");
        acc.setBalance(50.0);
        when(mockRepo.get("acc-1002")).thenReturn(acc);

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> service.withdraw("acc-1002", 100.0));
        assertTrue(ex.getMessage().toLowerCase().contains("insufficient"));

        // no writes happen on failure
        verify(mockRepo, never()).put(any());
        verify(mockTxnRepo, never()).put(any());
    }
}