package com.keerthana.bank_app.repository;

import com.keerthana.bank_app.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions,Long> {
        List<Transactions> findBySenderAccNumOrReceiverAccNumOrderByIdDesc(String senderAcc, String receiverAcc);
}
