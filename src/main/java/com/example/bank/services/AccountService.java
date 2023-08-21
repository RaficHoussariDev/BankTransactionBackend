package com.example.bank.services;

import com.example.bank.dtos.AccountDto;
import com.example.bank.dtos.TransactionDto;
import com.example.bank.enums.TransactionEnum;
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
import java.util.Optional;

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
    public Account transaction(Long accountId, Double amount) {
        log.info("making transaction for account with id {}", accountId);

        Account account = this.getAccountById(accountId);

        if(account != null) {
            account.setAmount(account.getAmount() + amount);
            this.accountRepository.save(account);
            log.info("account with id {} successfully updated", accountId);
            this.registerTransaction(account, amount);
        }

        return account;
    }

    @Transactional
    private void registerTransaction(Account account, Double amount) {
        log.info("registering transaction for account {}", account.getId());

        Transaction transaction = new Transaction();
        transaction.setType(amount >= 0 ? TransactionEnum.DEPOSIT : TransactionEnum.WITHDRAW);
        transaction.setAmount(Math.abs(amount));
        transaction.setAccount(account);

        this.transactionRepository.save(transaction);

        log.info("registered transaction for account {}", account.getId());
    }
}
