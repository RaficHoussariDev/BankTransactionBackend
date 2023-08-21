package com.example.bank.controllers;

import com.example.bank.dtos.CustomerDto;
import com.example.bank.exceptions.CustomValidationException;
import com.example.bank.exceptions.DtoValidation;
import com.example.bank.services.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    private final DtoValidation customerDtoValidation;

    public CustomerController(CustomerService customerService, DtoValidation customerDtoValidation) {
        this.customerService = customerService;
        this.customerDtoValidation = customerDtoValidation;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return new ResponseEntity<>(this.customerService.getCustomers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertCustomer(@RequestBody CustomerDto customerDto) {
        log.info("inserting new customer");
        List<String> validationErrors = this.customerDtoValidation.validateCustomer(customerDto);
        if(!validationErrors.isEmpty()) {
            log.info("failed in validating new customer");
            throw new CustomValidationException(validationErrors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.customerService.saveCustomer(customerDto), HttpStatus.CREATED);
    }
}
