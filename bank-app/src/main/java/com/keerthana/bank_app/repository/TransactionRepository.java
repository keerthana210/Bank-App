package com.keerthana.bank_app.repository;

import com.keerthana.bank_app.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions,Long> {
}
