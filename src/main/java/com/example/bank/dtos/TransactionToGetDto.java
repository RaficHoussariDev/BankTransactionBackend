package com.example.bank.dtos;

import com.example.bank.enums.TransactionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionToGetDto {
    private Double amount;
    private TransactionEnum type;
    private AccountToGetDto account;
}
