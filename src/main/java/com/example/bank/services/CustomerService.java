package com.example.bank.services;

import com.example.bank.dtos.CustomerDto;
import com.example.bank.mappers.CustomerMapper;
import com.example.bank.models.Customer;
import com.example.bank.repositories.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = this.customerRepository.findAll();

        return customers.stream().map(this.customerMapper::entityToDto).collect(Collectors.toList());
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = this.customerRepository.findById(id);

        if(customer.isEmpty()) {
            return null;
        }

        return customer.get();
    }

    @Transactional
    public Customer saveCustomer(@Valid CustomerDto customerDto) {
        log.info("customer validated successfully, inserting customer");
        Customer customer = this.customerMapper.dtoToEntity(customerDto);
        this.customerRepository.save(customer);

        log.info("customer successfully saved");
        return customer;
    }
}
