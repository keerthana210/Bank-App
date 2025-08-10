package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.Transactions;
import com.keerthana.bank_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transactions transactions) {
        transactionRepository.save(transactions);
    }

    public List<Transactions> findBySenderAccNumOrReceiverAccNumOrderByIdDesc(String sender, String receiver) {
       return transactionRepository.findBySenderAccNumOrReceiverAccNumOrderByIdDesc(sender,receiver);
    }
}
