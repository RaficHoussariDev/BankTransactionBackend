package com.example.bank.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    @PositiveOrZero(message = "Amount cannot have a negative value")
    private Double amount;

    @Column(name = "account_name", unique = true)
    @NotBlank(message = "Account Name required")
    private String accountName;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
