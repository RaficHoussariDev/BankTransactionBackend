package com.example.bank.mappers;

import com.example.bank.dtos.TransactionDto;
import com.example.bank.dtos.TransactionToGetDto;
import com.example.bank.models.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final ModelMapper modelMapper;
    private final AccountMapper accountMapper;

    public TransactionMapper(ModelMapper modelMapper, AccountMapper accountMapper) {
        this.modelMapper = modelMapper;
        this.accountMapper = accountMapper;
    }

    public TransactionDto entityToDto(Transaction transaction) {
        return this.modelMapper.map(transaction, TransactionDto.class);
    }

    public Transaction dtoToEntity(TransactionDto transactionDto) {
        return this.modelMapper.map(transactionDto, Transaction.class);
    }

    public TransactionToGetDto entityToTransactionToGetDto(Transaction transaction) {
        TransactionToGetDto transactionToGetDto = this.modelMapper.map(transaction, TransactionToGetDto.class);
        transactionToGetDto.setAccount(this.accountMapper.entityToAccountToGetDto(transaction.getAccount()));

        return transactionToGetDto;
    }
}
