package com.example.bank.services;

import com.example.bank.dtos.AccountDto;
import com.example.bank.enums.TransactionEnum;
import com.example.bank.mappers.AccountMapper;
import com.example.bank.models.Account;
import com.example.bank.models.Customer;
import com.example.bank.models.Transaction;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransactionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountMapper accountMapper;

    @Test
    public void save_account_test() {
        Customer customer = new Customer(1L, "Rafic");
        AccountDto accountDtoMock = new AccountDto(500.0, "New Account");
        Account accountMock = new Account(12L, 500.0, "New Account", customer);

        when(this.accountMapper.dtoToEntity(accountDtoMock)).thenReturn(accountMock);
        when(this.accountRepository.save(accountMock)).thenReturn(accountMock);

        Account resultAccount = this.accountService.createAccount(accountDtoMock, customer);

        assertNotNull(resultAccount);
        assertEquals(resultAccount.getId(), accountMock.getId());
        assertEquals(resultAccount.getCustomer(), accountMock.getCustomer());
    }

    @Test
    public void get_account_by_id_test() {
        Customer customer = new Customer(1L, "Rafic");
        Account accountMock = new Account(11L, 350.0, "Rafic Account", customer);

        when(this.accountRepository.findById(accountMock.getId())).thenReturn(Optional.of(accountMock));
        when(this.accountRepository.findById(5000L)).thenReturn(Optional.empty());

        Account resultAccount = this.accountService.getAccountById(accountMock.getId());
        Account nullAccount = this.accountService.getAccountById(5000L);

        assertNotNull(resultAccount);
        assertEquals(accountMock.getId(), resultAccount.getId());
        assertEquals(accountMock.getAccountName(), resultAccount.getAccountName());
        assertEquals(accountMock.getAmount(), resultAccount.getAmount());
        assertEquals(accountMock.getCustomer().getId(), resultAccount.getCustomer().getId());

        assertNull(nullAccount);
    }

    @Test
    public void transaction_test() {
        double positiveAmount = 50.0;
        double negativeAmount = -100.0;

        Customer customer = new Customer(1L, "Rafic");
        Account accountMock = new Account(11L, 350.0, "Rafic Account", customer);

        when(this.accountRepository.save(accountMock)).thenReturn(accountMock);

        Account deposit = this.accountService.transaction(accountMock, positiveAmount);
        assertEquals(deposit.getAmount() , 400.0);


        Account withdrawal = this.accountService.transaction(accountMock, negativeAmount);
        assertEquals(withdrawal.getAmount() , 300.0);
    }

    @Test
    public void register_transaction_test() {
        double positiveAmount = 100.0;
        double negativeAmount = -80.0;

        Customer customer = new Customer(1L, "Rafic");
        Account account = new Account(11L, 350.0, "Rafic Account", customer);

        Transaction transactionDepositMock = new Transaction(positiveAmount, account);
        Transaction transactionWithdrawalMock = new Transaction(negativeAmount, account);

        Transaction expectedTransactionDeposit = new Transaction(4L, positiveAmount, TransactionEnum.DEPOSIT, account);
        Transaction expectedTransactionWithdrawal = new Transaction(5L, negativeAmount, TransactionEnum.WITHDRAW, account);

        when(this.transactionRepository.save(transactionDepositMock)).thenReturn(expectedTransactionDeposit);
        when(this.transactionRepository.save(transactionWithdrawalMock)).thenReturn(expectedTransactionWithdrawal);

        Transaction depositTransaction = this.transactionRepository.save(transactionDepositMock);
        Transaction withdrawalTransaction = this.transactionRepository.save(transactionWithdrawalMock);

        assertNotNull(depositTransaction);
        assertNotNull(withdrawalTransaction);

        assertEquals(depositTransaction.getTransactionId(), expectedTransactionDeposit.getTransactionId());
        assertEquals(depositTransaction.getAmount(), expectedTransactionDeposit.getAmount());
        assertEquals(depositTransaction.getType(), expectedTransactionDeposit.getType());
        assertEquals(depositTransaction.getAccount(), expectedTransactionDeposit.getAccount());

        assertEquals(withdrawalTransaction.getTransactionId(), expectedTransactionWithdrawal.getTransactionId());
        assertEquals(withdrawalTransaction.getAmount(), expectedTransactionWithdrawal.getAmount());
        assertEquals(withdrawalTransaction.getType(), expectedTransactionWithdrawal.getType());
        assertEquals(withdrawalTransaction.getAccount(), expectedTransactionWithdrawal.getAccount());
    }
}
