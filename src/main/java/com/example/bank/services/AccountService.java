package com.example.bank.services;

import com.example.bank.dtos.AccountDto;
import com.example.bank.dtos.AccountToGetDto;
import com.example.bank.dtos.TransactionToGetDto;
import com.example.bank.mappers.AccountMapper;
import com.example.bank.mappers.TransactionMapper;
import com.example.bank.models.Account;
import com.example.bank.models.Customer;
import com.example.bank.models.Transaction;
import com.example.bank.repositories.AccountRepository;

import com.example.bank.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public List<AccountToGetDto> getAllAccounts() {
        List<Account> accounts = this.accountRepository.findAllAccounts();

        return accounts.stream().map(this.accountMapper::entityToAccountToGetDto).collect(Collectors.toList());
    }

    @Transactional
    public Account createAccount(@Valid AccountDto accountDto, Customer customer) {
        log.info("account validated successfully, inserting account");
        Account account = this.accountMapper.dtoToEntity(accountDto);
        account.setCustomer(customer);
        this.accountRepository.save(account);

        log.info("account successfully saved");
        return account;
    }

    public Account getAccountById(Long id) {
        log.info("fetching account with id {}", id);
        Optional<Account> account = this.accountRepository.findById(id);

        if(account.isEmpty()) {
            return null;
        }

        return account.get();
    }

    @Transactional
    public Account transaction(Account account, Double amount) {
        log.info("making transaction for account with id {}", account.getId());

        account.setAmount(account.getAmount() + amount);
        this.accountRepository.save(account);
        log.info("account with id {} successfully updated", account.getId());
        this.registerTransaction(account, amount);

        return account;
    }

    @Transactional
    private Transaction registerTransaction(Account account, double amount) {
        log.info("registering transaction for account {}", account.getId());

        Transaction transaction = new Transaction(amount, account);

        this.transactionRepository.save(transaction);

        log.info("successfully registered transaction for account {}", account.getId());

        return transaction;
    }

    public List<TransactionToGetDto> getAllTransactions() {
        List<Transaction> transactions = this.transactionRepository.findAllTransactions();

        return transactions.stream().map(this.transactionMapper::entityToTransactionToGetDto).collect(Collectors.toList());
    }
}
