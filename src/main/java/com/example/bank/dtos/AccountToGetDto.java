package com.example.bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountToGetDto {
    private Double amount;
    private String accountName;
    private CustomerDto customer;
}
