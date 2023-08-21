package com.example.bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @Min(value = 0, message = "Amount cannot have a negative value")
    private Double amount;

    @NotBlank(message = "Account Name required")
    private String accountName;
}
