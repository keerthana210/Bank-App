package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.*;
import com.keerthana.bank_app.service.TransactionService;
import com.keerthana.bank_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/transactions/")
public class TransactionController {

    private UserService userService;
    private TransactionService transactionService;

    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping("/send-money")
    public ResponseEntity<String> sendMoney(@RequestBody SendMoney sendMoney) {
        Transactions transactions = new Transactions();
        transactions.setSenderAccNum(sendMoney.getSenderAccNum());
        transactions.setReceiverAccNum(sendMoney.getReceiverAccNum());
        transactions.setAmount(sendMoney.getAmountSent());

        Optional<User> senderOpt = userService.getUserByAccNumber(sendMoney.getSenderAccNum());
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

        if (!sender.getTransactionPin().equals(sendMoney.getSenderTransactionPin())) {
            transactions.setStatus("Failed");
            transactions.setMessage("Invalid transaction PIN");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid transaction PIN");
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
        transactions.setStatus("Passed");
        transactions.setMessage("Money Transferred successfully");
        transactionService.save(transactions);
        return ResponseEntity.ok("Money transferred successfully");
    }

    @PostMapping("/withdraw-money")
    public ResponseEntity<String> withdrawMoney(@RequestBody WithdrawMoney withdrawMoney){
        Transactions transactions = new Transactions();
        transactions.setReceiverAccNum("Null");
        Optional<User> getAccHolder = userService.getUserByAccNumber(withdrawMoney.getAccNumber());
        if(getAccHolder.isEmpty()){
            transactions.setStatus("Failed");
            transactions.setMessage("Account does not exist!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account does not exist!");
        }
        User accHolder = getAccHolder.get();

        if(!(accHolder.getTransactionPin()).equals(withdrawMoney.getAccTransactionPin())){
            System.out.println(accHolder.getTransactionPin()+" "+withdrawMoney.getAccTransactionPin());
            transactions.setStatus("Failed");
            transactions.setMessage("Incorrect transaction pin!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect transaction pin!");
        }
        if(accHolder.getAccBalance()<withdrawMoney.getAmount()){
            transactions.setStatus("Failed");
            transactions.setMessage("Insufficient balance!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance!");
        }

        accHolder.setAccBalance(accHolder.getAccBalance()-withdrawMoney.getAmount());

        transactions.setSenderAccNum(withdrawMoney.getAccNumber());
        transactions.setAmount(withdrawMoney.getAmount());
        transactions.setStatus("Passed");
        transactions.setMessage("Money Withdrawn Successfully");
        transactionService.save(transactions);
        return ResponseEntity.ok("Money withdrawn successfully!");
    }

    @PostMapping("/deposit-money")
    public ResponseEntity<String> depositMoney(@RequestBody DepositMoney depositMoney){
        Transactions transactions = new Transactions();
        transactions.setReceiverAccNum("Null");
        Optional<User> getAccHolder = userService.getUserByAccNumber(depositMoney.getAccNumber());
        if(getAccHolder.isEmpty()){
            transactions.setStatus("Failed");
            transactions.setMessage("Account does not exist!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account does not exist!");
        }
        User accHolder = getAccHolder.get();
        if(!(accHolder.getTransactionPin()).equals(depositMoney.getAccTransactionPin())){
            System.out.println(accHolder.getTransactionPin()+" "+depositMoney.getAccTransactionPin());
            transactions.setStatus("Failed");
            transactions.setMessage("Incorrect transaction pin!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect transaction pin!");
        }
        accHolder.setAccBalance(accHolder.getAccBalance()+depositMoney.getAmountDeposited());

        transactions.setSenderAccNum(depositMoney.getAccNumber());
        transactions.setAmount(depositMoney.getAmountDeposited());
        transactions.setStatus("Passed");
        transactions.setMessage("Money Deposited Successfully");
        transactionService.save(transactions);
        return ResponseEntity.ok("Money Deposited successfully!");
    }
}
