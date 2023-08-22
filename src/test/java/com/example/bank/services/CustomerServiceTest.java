package com.example.bank.services;

import com.example.bank.dtos.CustomerDto;
import com.example.bank.mappers.CustomerMapper;
import com.example.bank.models.Customer;
import com.example.bank.repositories.CustomerRepository;

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
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Test
    public void getCustomerByIdTest() {
        Customer customerMock = new Customer(1L, "Rafic");

        when(this.customerRepository.findById(customerMock.getId())).thenReturn(Optional.of(customerMock));
        when(this.customerRepository.findById(5000L)).thenReturn(Optional.empty());

        Customer resultCustomer = this.customerService.getCustomerById(customerMock.getId());
        Customer nullCustomer = this.customerService.getCustomerById(5000L);

        assertNotNull(resultCustomer);
        assertEquals(customerMock.getId(), resultCustomer.getId());
        assertEquals(customerMock.getName(), resultCustomer.getName());

        assertNull(nullCustomer);
    }

    @Test
    public void saveCustomerTest() {
        CustomerDto customerDtoMock = new CustomerDto("Kevin");
        Customer customerMock = new Customer(6L, "Kevin");

        when(this.customerMapper.dtoToEntity(customerDtoMock)).thenReturn(customerMock);
        when(this.customerRepository.save(customerMock)).thenReturn(customerMock);

        Customer resultCustomer = this.customerService.saveCustomer(customerDtoMock);

        assertNotNull(resultCustomer);
        assertEquals(resultCustomer.getId(), customerMock.getId());
        assertEquals(resultCustomer.getName(), customerMock.getName());
    }
}
