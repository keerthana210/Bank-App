# 💳 HiraBank – Spring Boot Banking Application

**HiraBank** is a Spring Boot-based banking system that allows admins and users to log in and perform basic banking operations such as deposits, withdrawals, and money transfers. This project is a work-in-progress personal backend portfolio app aimed at demonstrating real-world skills in Java, Spring Boot, and RESTful API design.

---

## 🚀 Features Implemented (So Far)

- ✅ Admin & User login (with role separation)
- ✅ Password encryption using BCrypt
- ✅ Deposit, Withdraw, and Send Money functionalities
- ✅ Database integration using MySQL
- ✅ Layered architecture (Controller → Service → Repository → Model)
- ✅ RESTful APIs using Spring Boot

---

## 📌 Features Coming Soon

- 🔐 JWT-based login and secure authentication
- 👤 User session-based handling of transactions
- 📄 Swagger UI for API documentation
- 🚀 Deployment on Railway or Render
- 📈 Transaction history & account statements

---

## 🧰 Tech Stack

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

## 🗂 Project Structure
```
bank-app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── keerthana/
│       │           └── bank_app/
│       │               ├── configuration/     # Security configuration classes
│       │               ├── controller/        # REST controllers (AdminController, UserController)
│       │               ├── enums/             # Enum definitions (Role, AccessLevel, etc.)
│       │               ├── model/             # Entity classes (User, Admin, etc.)
│       │               ├── repository/        # JPA repositories  
│       │               ├── service/           # Service layer for business logic
│       │               └── BankAppApplication.java # Main Spring Boot application class
│       └── resources/
│           ├── static/                        # Static files (HTML, CSS, JS if any)
│           ├── templates/                     # Thymeleaf templates (if used)
│           └── application.properties         # Configuration for DB, port, etc.
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
├── pom.xml                                    # Maven dependencies

```

Made with ❤️ by Keerthana 
Java Backend Developer | Spring Boot Enthusiast

📧 keerthiviolet2@gmail.com
🔗 LinkedIn
🔗 GitHub
