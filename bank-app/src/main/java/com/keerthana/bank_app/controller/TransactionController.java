package com.keerthana.bank_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @RequestMapping("/sendMoney")
    public String sendMoney(){

        return "MoneySent";
    }
    @RequestMapping("/withdrawMoney")
    public String withdrawMoney(){
        return "moneyWithdrawn";
    }

    @RequestMapping("/depositMoney")
    public String depositMoney(){
        return "moneyDeposited";
    }
}
