package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.Transactions;
import com.keerthana.bank_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transactions transactions) {
        transactionRepository.save(transactions);
    }
}
