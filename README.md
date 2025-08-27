# 🏦 Loan Management System (LMS) — React + Spring Boot (with BMS Integration)

A full-stack Loan Management System built with **React (TypeScript)** on the frontend and **Spring Boot (Java)** on the backend.
It integrates with a lightweight Bank Management System (BMS) for account verification, loan disbursement, and repayments.

This repository is structured for easy local setup, extension, and deployment.

## 📌 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Folder Structure](#folder-structure)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

---

## 🚀 Features
### For Customers

- Register & login (JWT-based authentication)

- Bank account verification via micro-deposit (BMS)

- Apply for loans (auto-calculated EMI)

- View active loans, applications & EMI schedules

- Repay loan amounts online

- Update profile & change password securely

### For Admins

- Dashboard with loan statistics (pending, approved, disbursed, rejected)

- Review loan applications with BMS account snapshots

- Approve & disburse loans (auto-generates EMI schedule)

- Reject loan applications

- View all applications & repayment history
- 
---

## ⚙️🛠️Tech Stack

- 🎨🖥️ **Frontend**: React + Vite + TypeScript + Axios

- ⚙️🛠️ **Backend**: Spring Boot + Spring Security + JWT

 - 🗄️💾 **Database**: PostgreSQL

-  🌐 **Integration**: BMS client for bank operations

---

  ## Installations(Running the Project Locally)
### 🛠️ Prerequisites

- Java 17+

- Maven or Gradle

- Node.js 18+ & npm / yarn

- any database server like PostgreSQL 14+, MySQL,

- (Optional) Docker for DB setup

🔧 Backend Setup (Spring Boot)
### Create PostgreSQL DB
create db lms

### Or using Docker
docker run --name lms-postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=lms \
-p 5432:5432 \
-d postgres:14


- Edit backend/src/main/resources/application.properties:

- server.port=8081
- spring.datasource.url=jdbc:postgresql://localhost:5432/lms
- spring.datasource.username=postgres
- spring.datasource.password=postgres
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true

# JWT Configuration
app.jwt.secret=your-secret-key
app.jwt.expiration=86400000


## Run the backend:

cd backend
mvn spring-boot:run

## 💻 Frontend Setup (React + Vite)
- cd frontend
- npm install
- npm run dev

- Frontend: http://localhost:5173

- Backend: http://localhost:8081

## 🌐 Environment Variables

Create .env in frontend/:

- VITE_API_BASE=http://localhost:8081/api/lms
- VITE_PUBLIC_BASE=http://localhost:8081

## 🔄 Key Flows
- 1️⃣ Bank Verification

→ LMS → BMS → Micro-deposit → Verification → Credit Score Update

- 2️⃣ Loan Application

→ Check verified bank → Calculate EMI → Save as PENDING → Update credit score

- 3️⃣ Admin Approval

→ Fetch BMS snapshot → Approve → LMS disburses loan → EMI schedule generated

- 4️⃣ Loan Repayment

→ Repayment via BMS → LMS updates EMI & credit score

## 📡 API Reference
Authentication
- POST /login
- POST /register
- GET /api/lms/profile
- PUT /api/lms/updatePassword

### Bank APIs
- POST /api/lms/bank/verify
- POST /api/lms/bank/confirm

### Loan APIs
- POST /api/lms/loan/apply
- GET /api/lms/applications/{accountNumber}
- GET /api/lms/active/{accountNumber}

### Admin APIs
- GET /api/lms/loan/pending
- POST /api/lms/loan/approve
- POST /api/lms/loan/reject
- GET /api/lms/admin/dashboard

### Repayment
- POST /api/lms/loan/repay

## 🪙 Bank Management System (BMS)

- A demo banking microservice built using Spring Boot that integrates with LMS via JWT-secured APIs.

### Features:

- REST APIs for micro-deposits, loan disbursements, repayments

- JWT authentication & secure ngrok tunneling

- Handles account verification & transaction tracking

## BMS Endpoints
- GET /api/bank/hello    	GET	Health check
- GET /api/bank/verify		Send micro-deposit
- POST /api/bank/verify-deposit	Confirm micro-deposit
- POST /api/bank/loan	   	Disburse loan
- POST /api/bank/repay	  	Repay loan amount
### 🗂️ Database Schema (High Level)

- users → Stores customer & admin profiles

- bank_account → Bank verification status

- loan_application → Pending loan requests

- loan → Approved loans & EMI details

- loan_emi_schedule → EMI tracking per loan

- repayment → Loan repayments

- credit_score → Customer credit scoring

### ⚠️ Common Pitfalls & Fixes

- CORS Issues → Add Spring CorsConfig

- 400 on Password Update → Use protected API client

- Wrong Dates → Check appliedDate in backend response

## 🛤 Roadmap

✅ JWT Auth & BMS Integration
✅ Loan Application, Approval, EMI & Repayment
🔄 Multi-bank account support
📨 Email Notifications
📈 Reports & Analytics
🐳 Docker Compose Setup

### 🤝 Contributing

- Fork the repo

- Create a feature branch

- Commit changes

- Open a PR with screenshots & test notes
```pleintext
 📂 folder Structure
loan-management-system/
├── backend/
│   ├── src/main/java/com/lms/
│   │   ├── controller/       # REST controllers
│   │   ├── service/          # Business logic (Loan, CreditScore, Mail, etc.)
│   │   ├── model/            # Entities (User, Loan, EMI, Repayment, etc.)
│   │   ├── dto/              # DTOs for requests & responses
│   │   ├── repository/       # Spring Data repositories
│   │   ├── security/         # JWT auth & filters
│   │   └── client/           # BMS API client
│   └── src/main/resources/
│       └── application.properties
└── frontend/
    └── src/
        ├── api/             # Axios API clients
        ├── components/      # UI components
        ├── contexts/        # Auth & session handling
        ├── lib/             # Axios configs (api.ts, publicApi.ts)
        ├── pages/           # UI pages (Dashboard, Loans, Profile, etc.)
        └── services/        # Auth & Loan services
BMS/
├── .classpath
├── .gitattributes
├── .gitignore
├── .project
├── HELP.md
├── pom.xml  (if using Maven)
├── target/           # Compiled files (should be ignored in .gitignore)
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── BMS/
│   │   │           └── BMS/
│   │   │               ├── BmsApplication.java
│   │   │               │
│   │   │               ├── Config/
│   │   │               │   ├── JwtFilter.java
│   │   │               │   ├── JwtUtil.java
│   │   │               │   └── SecurityConfig.java
│   │   │               │
│   │   │               ├── Controller/
│   │   │               │   └── BankController.java
│   │   │               │
│   │   │               ├── DTO/
│   │   │               │   └── LoanSummaryDTO.java
│   │   │               │
│   │   │               ├── Models/
│   │   │               │   ├── BankFund.java
│   │   │               │   ├── Customer.java
│   │   │               │   ├── MicroDeposit.java
│   │   │               │   └── Transaction.java
│   │   │               │
│   │   │               ├── Repo/
│   │   │               │   ├── BankFundRepository.java
│   │   │               │   ├── CustomerRepository.java
│   │   │               │   ├── MicroDepositRepository.java
│   │   │               │   └── TransactionRepository.java
│   │   │               │
│   │   │               └── Service/
│   │   │                   ├── BankService.java
│   │   │                   └── MailService.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── BMS/
│                   └── BMS/
│                       └── BmsApplicationTests.java
│
└── README.md


  📄 License

MIT License — Free to use, modify & distribute.
