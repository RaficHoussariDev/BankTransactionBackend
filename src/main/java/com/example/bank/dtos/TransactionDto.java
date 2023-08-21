package com.example.bank.dtos;

import com.example.bank.enums.TransactionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Double amount;
    private TransactionEnum type;
}
