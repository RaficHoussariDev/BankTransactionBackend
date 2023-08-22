package com.example.bank.models;

import com.example.bank.enums.TransactionEnum;
import com.example.bank.validations.paymentMethod.ValidPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "transaction_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    public Transaction(double amount, Account account) {
        this.amount = Math.abs(amount);
        this.type = amount >= 0 ? TransactionEnum.DEPOSIT : TransactionEnum.WITHDRAW;
        this.account = account;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long transactionId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @ValidPaymentMethod
    private TransactionEnum type;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
