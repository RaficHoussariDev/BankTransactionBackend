package com.example.bank.mappers;

import com.example.bank.dtos.AccountDto;
import com.example.bank.models.Account;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDto entityToDto(Account account) {
        return this.modelMapper.map(account, AccountDto.class);
    }

    public Account dtoToEntity(AccountDto accountDto) {
        return this.modelMapper.map(accountDto, Account.class);
    }
}
