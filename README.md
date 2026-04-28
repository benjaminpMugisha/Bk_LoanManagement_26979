# 🏦 Bank of Kigali — Loan Management System

> **OOP Java Assignment | AUCA Software Engineering | Student ID: _26979**

A secure, scalable, console-based Loan Management System built in Java,
demonstrating all core Object-Oriented Programming principles.

---

## 📁 Project Structure

```
src/Com/bankofkigali/
├── loan/
│   ├── Loan.java               ← Abstract base class (8 abstract methods)
│   ├── Payable.java            ← Interface (3 payment methods)
│   ├── LoanManager.java        ← Extends Loan + implements Payable
│   ├── PersonalLoan.java       ← Subclass: purposeOfLoan
│   ├── HomeLoan.java           ← Subclass: propertyValue
│   ├── CarLoan.java            ← Subclass: vehicleMake
│   ├── BusinessLoan.java       ← Subclass: collateralType
│   ├── StudentLoan.java        ← Subclass: universityName
│   └── AgriculturalLoan.java   ← Subclass: farmSizeHectares
├── models/
│   ├── Customer.java
│   ├── LoanManager.java
│   └── Repayment.java
├── utils/
│   ├── InputValidator.java     ← All input validation logic
│   └── LoanFactory.java        ← Polymorphic factory pattern
└── main/
    └── LoanSystem.java         ← main() — interactive console UI
```

---

## 🎯 OOP Concepts Demonstrated

| Concept | Implementation |
|---|---|
| **Encapsulation** | All attributes private; accessed via getters/setters |
| **Abstraction** | `Loan` abstract class with 8 abstract methods |
| **Interface** | `Payable` interface with 3 methods |
| **Inheritance** | 6 loan subtypes extend `LoanManager` which extends `Loan` |
| **Polymorphism** | `LoanFactory` returns different subtypes; methods overridden per type |
| **Constructors** | Default & parameterized constructors in every class |
| **Method Overriding** | `toString()`, `approveLoan()`, `checkEligibility()`, `calculateInterest()` |

---

## 🚀 How to Build & Run

### Option 1 — Run JAR directly
```bash
java -jar LoanSystem.jar
```

### Option 2 — Compile from source (Windows CMD)
```cmd
mkdir out
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out com.bankofkigali.main.LoanSystem
```

### Option 3 — Docker
```bash
docker build -t bk-loan-system .
docker run -it bk-loan-system
```

---

## 🐳 Dockerfile

```dockerfile
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY LoanSystem.jar .
ENTRYPOINT ["java", "-jar", "LoanSystem.jar"]
```

---

## ✅ Features

- Register customers with validated National ID and phone number
- Apply for **6 loan types**: Personal, Home, Car, Business, Student, Agricultural
- Automatic **eligibility checking** based on income and loan-type rules
- **EMI calculation** using standard amortization formula
- Make payments with **receipt generation**
- **Duplicate ID prevention**
- Full **input validation** with meaningful error messages and re-prompts

---

## 🛡️ Validation Rules

| Field | Rule |
|---|---|
| Customer ID | Format: CUST-XXX |
| National ID | Exactly 16 digits |
| Phone Number | Rwanda format: `07XXXXXXXX` |
| Loan Amount | Positive, max RWF 500,000,000 |
| Interest Rate | 0% – 50% |
| Duration | Positive integer (months) |
| Loan Type | Must be one of 6 valid types |

---

## 👨‍💻 Author

**Mugisha Prince Benjamin Kamanzi**
Student ID: **_26979**
Third-Year Software Engineering Student
Adventist University of Central Africa (AUCA), Kigali, Rwanda
