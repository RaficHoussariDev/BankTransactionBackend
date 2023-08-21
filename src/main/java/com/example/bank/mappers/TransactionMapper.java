package com.example.bank.mappers;

import com.example.bank.dtos.TransactionDto;
import com.example.bank.models.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDto entityToDto(Transaction transaction) {
        return this.modelMapper.map(transaction, TransactionDto.class);
    }

    public Transaction dtoToEntity(TransactionDto transactionDto) {
        return this.modelMapper.map(transactionDto, Transaction.class);
    }
}
