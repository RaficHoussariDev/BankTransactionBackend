package com.example.bank.controllers;

import com.example.bank.dtos.AccountDto;
import com.example.bank.dtos.AccountToGetDto;
import com.example.bank.dtos.TransactionToGetDto;
import com.example.bank.exceptions.CustomValidationException;
import com.example.bank.exceptions.DtoValidation;
import com.example.bank.models.Account;
import com.example.bank.models.Customer;
import com.example.bank.services.AccountService;
import com.example.bank.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final CustomerService customerService;
    private final DtoValidation accountDtoValidation;

    public AccountController(AccountService accountService, CustomerService customerService, DtoValidation accountDtoValidation) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.accountDtoValidation = accountDtoValidation;
    }

    @GetMapping
    public ResponseEntity<List<AccountToGetDto>> getAccounts() {
        log.info("fetching all the accounts");

        return new ResponseEntity<>(this.accountService.getAllAccounts(), HttpStatus.OK);
    }

    @PostMapping("/create/{customerId}")
    public ResponseEntity createAccount(@PathVariable Long customerId, @RequestBody AccountDto accountDto) {
        log.info("inserting new customer");

        Customer customer = this.customerService.getCustomerById(customerId);

        if(customer == null) {
            log.info("failed to find a customer with the id of {}", customerId.toString());
            throw new CustomValidationException(List.of("Customer with id " + customerId + " not found"), HttpStatus.NOT_FOUND);
        }

        List<String> validationErrors = this.accountDtoValidation.validateAccount(accountDto);
        if(!validationErrors.isEmpty()) {
            log.info("failed in validating new account");
            throw new CustomValidationException(validationErrors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.accountService.createAccount(accountDto, customer), HttpStatus.CREATED);
    }

    @PostMapping("/transaction/{accountId}")
    public ResponseEntity transaction(@PathVariable Long accountId, @RequestParam Double amount) {
        log.info("{}", amount >= 0 ? "depositing money" : "withdrawing money");

        Account account = this.accountService.getAccountById(accountId);

        if(account == null) {
            log.info("failed to find an account with the id of {}", accountId);
            throw new CustomValidationException(List.of("Account with id " + accountId + " not found"), HttpStatus.NOT_FOUND);
        }

        if(amount < 0 && account.getAmount() < Math.abs(amount)) {
            log.info("amount to withdraw is higher than the amount saved in the account {}", accountId);
            throw new CustomValidationException(List.of("Account " + accountId + " does not have enough money to withdraw this amount"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.accountService.transaction(account, amount), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionToGetDto>> getAllTransactions() {
        log.info("fetching all the transactions");

        return new ResponseEntity<>(this.accountService.getAllTransactions(), HttpStatus.OK);
    }
}
