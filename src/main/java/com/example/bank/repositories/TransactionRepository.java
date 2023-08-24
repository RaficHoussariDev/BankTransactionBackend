package com.example.bank.repositories;

import com.example.bank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT a FROM Transaction a JOIN FETCH a.account")
    List<Transaction> findAllTransactions();
}
