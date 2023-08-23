package com.example.bank.mappers;

import com.example.bank.dtos.AccountDto;
import com.example.bank.dtos.AccountToGetDto;
import com.example.bank.models.Account;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;
    private final CustomerMapper customerMapper;

    public AccountMapper(ModelMapper modelMapper, CustomerMapper customerMapper) {
        this.modelMapper = modelMapper;
        this.customerMapper = customerMapper;
    }

    public AccountDto entityToDto(Account account) {
        return this.modelMapper.map(account, AccountDto.class);
    }

    public Account dtoToEntity(AccountDto accountDto) {
        return this.modelMapper.map(accountDto, Account.class);
    }

    public AccountToGetDto entityToAccountToGetDto(Account account) {
        AccountToGetDto accountToGetDto = this.modelMapper.map(account, AccountToGetDto.class);
        accountToGetDto.setCustomerDto(this.customerMapper.entityToDto(account.getCustomer()));

        return accountToGetDto;
    }
}
