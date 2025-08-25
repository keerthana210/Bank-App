package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.*;
import com.keerthana.bank_app.service.TransactionService;
import com.keerthana.bank_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/transactions/")
public class TransactionController {
    private UserService userService;
    private TransactionService transactionService;
    private PasswordEncoder passwordEncoder;

    public TransactionController(UserService userService, TransactionService transactionService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.passwordEncoder = passwordEncoder;
    }

    private String getLoggedInUserAccNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciples userDetails = (UserPrinciples) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @PostMapping("/send-money")
    public ResponseEntity<String> sendMoney(@RequestBody SendMoney sendMoney) {
        String senderAccNum = getLoggedInUserAccNumber();
        sendMoney.setSenderAccNum(senderAccNum);
        Transactions transactions = new Transactions();
        transactions.setSenderAccNum(senderAccNum);
        transactions.setReceiverAccNum(sendMoney.getReceiverAccNum());
        transactions.setAmount(sendMoney.getAmountSent());
        Optional<User> senderOpt = userService.getUserByAccNumber(senderAccNum);
        if (senderOpt.isEmpty()) {
            transactions.setStatus("Failed");
            transactions.setMessage("Sender not Found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found");
        }
        User sender = senderOpt.get();
        Optional<User> receiverOpt = userService.getUserByAccNumber(sendMoney.getReceiverAccNum());
        if (receiverOpt.isEmpty()) {
            transactions.setStatus("Failed");
            transactions.setMessage("Receiver not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found");
        }
        if (!passwordEncoder.matches(sendMoney.getSenderTransactionPin(), sender.getTransactionPin())) {
            transactions.setStatus("Failed");
            transactions.setMessage("Incorrect transaction pin!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect transaction pin!");
        }
        if (sender.getAccBalance() < sendMoney.getAmountSent()) {
            transactions.setStatus("Failed");
            transactions.setMessage("Insufficient balance");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }
        User receiver = receiverOpt.get();
        sender.setAccBalance(sender.getAccBalance() - sendMoney.getAmountSent());
        receiver.setAccBalance(receiver.getAccBalance() + sendMoney.getAmountSent());
        userService.saveUser(sender);
        userService.saveUser(receiver);
        transactions.setAccountBalance(sender.getAccBalance());
        transactions.setStatus("Passed");
        transactions.setMessage("Money Transferred successfully");
        transactionService.save(transactions);
        return ResponseEntity.ok("Money transferred successfully");
    }

    @PostMapping("/withdraw-money")
    public ResponseEntity<String> withdrawMoney(@RequestBody WithdrawMoney withdrawMoney) {
        String accNumber = getLoggedInUserAccNumber();
        withdrawMoney.setAccNumber(accNumber);
        Transactions transactions = new Transactions();
        transactions.setReceiverAccNum("Null");
        Optional<User> getAccHolder = userService.getUserByAccNumber(accNumber);
        if (getAccHolder.isEmpty()) {
            transactions.setStatus("Failed");
            transactions.setMessage("Account does not exist!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account does not exist!");
        }
        User accHolder = getAccHolder.get();
        if (!passwordEncoder.matches(withdrawMoney.getAccTransactionPin(), accHolder.getTransactionPin())) {
            transactions.setStatus("Failed");
            transactions.setMessage("Incorrect transaction pin!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect transaction pin!");
        }
        if (accHolder.getAccBalance() < withdrawMoney.getAmount()) {
            transactions.setStatus("Failed");
            transactions.setMessage("Insufficient balance!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance!");
        }
        accHolder.setAccBalance(accHolder.getAccBalance() - withdrawMoney.getAmount());
        transactions.setAccountBalance(accHolder.getAccBalance());
        transactions.setSenderAccNum(accNumber);
        transactions.setAmount(withdrawMoney.getAmount());
        transactions.setStatus("Passed");
        transactions.setMessage("Money Withdrawn Successfully");
        transactionService.save(transactions);
        return ResponseEntity.ok("Money withdrawn successfully!");
    }

    @PostMapping("/deposit-money")
    public ResponseEntity<String> depositMoney(@RequestBody DepositMoney depositMoney) {
        String accNumber = getLoggedInUserAccNumber();
        depositMoney.setAccNumber(accNumber);
        Transactions transactions = new Transactions();
        transactions.setReceiverAccNum("Null");
        Optional<User> getAccHolder = userService.getUserByAccNumber(accNumber);
        if (getAccHolder.isEmpty()) {
            transactions.setStatus("Failed");
            transactions.setMessage("Account does not exist!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account does not exist!");
        }
        User accHolder = getAccHolder.get();
        if (!passwordEncoder.matches(depositMoney.getAccTransactionPin(), accHolder.getTransactionPin())) {
            transactions.setStatus("Failed");
            transactions.setMessage("Incorrect transaction pin!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect transaction pin!");
        }
        accHolder.setAccBalance(accHolder.getAccBalance() + depositMoney.getAmountDeposited());
        transactions.setAccountBalance(accHolder.getAccBalance());
        transactions.setSenderAccNum(accNumber);
        transactions.setAmount(depositMoney.getAmountDeposited());
        transactions.setStatus("Passed");
        transactions.setMessage("Money Deposited Successfully");
        transactionService.save(transactions);
        return ResponseEntity.ok("Money Deposited successfully!");
    }

    @GetMapping("/transaction-list")
    public ResponseEntity<List<Transactions>> getTransactions() {
        String accNumber = getLoggedInUserAccNumber();
        List<Transactions> transactions = transactionService
                .findBySenderAccNumOrReceiverAccNumOrderByIdDesc(accNumber, null);
        return ResponseEntity.ok(transactions);
    }
}
