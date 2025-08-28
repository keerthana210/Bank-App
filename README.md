# HiraBank – Spring Boot Banking Application

**HiraBank** is a Spring Boot-based banking system that allows admins and users to log in and perform basic banking operations such as deposits, withdrawals, and money transfers. This project is a work-in-progress personal backend portfolio app aimed at demonstrating real-world skills in Java, Spring Boot, and RESTful API design.

---

## Features Implemented

-  Admin & User login (with role separation)
-  Password encryption using BCrypt
-  Deposit, Withdraw, and Send Money functionalities
-  Database integration
-  Layered architecture (Controller → Service → Repository → Model)
-  RESTful APIs using Spring Boot

---

## Tech Stack

| Layer | Tech                     |
|-------|--------------------------|
| Language | Java 17                  |
| Framework | Spring Boot 3            |
| Security | Spring Security (BCrypt) |
| Database | MySQL                    |
| ORM | JPA / Hibernate          |
| API Testing | Postman                  |
| Version Control | Git & GitHub             |

---

## Project Structure
```
bank-app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── keerthana/
│       │           └── bank_app/
│       │               ├── configuration/     
│       │               ├── controller/         
│       │               ├── enums/             
│       │               ├── model/             
│       │               ├── repository/         
│       │               ├── service/           
│       │               └── BankAppApplication.java 
│       └── resources/
│           ├── static/                       
│           │   ├──adminView/
│           │   ├──userView/
│           │   └──index.html
│           ├── templates/                    
│           └── application.properties         
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── keerthana/
│                   └── bank_app/
│                       ├── service/
│                       │   └── AdminServiceTest.java
│                       │   └── TransactionServiceTest.java
│                       ├── controller/
│                       │   └── UserControllerTest.java
│                       └── integration/
│                           └── BankAppIntegrationTest.java
│
│
├── .gitignore
├── README.md
├── pom.xml                                   

```

Made with ❤️ by Keerthana 

Java Backend Developer | Spring Boot Enthusiast

📧 keerthiviolet2@gmail.com
🔗 [LinkedIn](https://www.linkedin.com/in/keerthi-t/)
🔗 [GitHub](https://github.com/keerthiviolet)
