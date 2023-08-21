package com.example.bank.mappers;

import com.example.bank.dtos.CustomerDto;
import com.example.bank.models.Customer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDto entityToDto(Customer customer) {
        return this.modelMapper.map(customer, CustomerDto.class);
    }

    public Customer dtoToEntity(CustomerDto customerDto) {
        return this.modelMapper.map(customerDto, Customer.class);
    }
}
