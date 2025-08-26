🏦 Loan Management System (LMS) — React + Spring Boot (with BMS Integration)

A full-stack Loan Management System built with React (TypeScript) on the frontend and Spring Boot (Java) on the backend.
It integrates with a lightweight Bank Management System (BMS) for account verification, loan disbursement, and repayments.

This repository is structured for easy local setup, extension, and deployment.

📑 Table of Contents

🚀 Features

⚙️ Architecture

📂 Monorepo Structure

🛠️ Prerequisites

🔧 Backend Setup (Spring Boot)

💻 Frontend Setup (React + Vite)

🌐 Environment Variables

🔄 Key Flows

📡 API Reference

🪙 Bank Management System (BMS)

🗂️ Database Schema

⚠️ Common Pitfalls & Fixes

🛤 Roadmap

🤝 Contributing

📄 License

🚀 Features
For Customers

Register & login (JWT-based authentication)

Bank account verification via micro-deposit (BMS)

Apply for loans (auto-calculated EMI)

View active loans, applications, and EMI schedules

Repay loan amounts online

Update profile & change password securely

For Admins

Dashboard with loan statistics: pending, approved, disbursed, rejected

Review pending loan applications with BMS account snapshots

Approve & disburse loans (auto-generates EMI schedule)

Reject applications

View all applications and repayment history

⚙️ Architecture
flowchart LR
  A[Browser (React + Vite)] -->|Axios| B[Spring Boot API (/api/lms)]
  B --> C[AuthService / JWT]
  B --> D[LoanService]
  B --> E[CreditScoreService]
  B --> F[MailService]
  B --> G[BmsClient (Bank Mgmt System)]
  D --> H[(PostgreSQL)]
  C --> H
  E --> H
  D -->|Disburse / Repay / Verify| G


Frontend: React + Vite + TypeScript + Axios

Backend: Spring Boot + Spring Security + JWT

Database: PostgreSQL

Integration: BMS client for bank operations

Deployment: Docker-ready setup

📂 Monorepo Structure
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

🛠️ Prerequisites

Java 17+

Maven (or Gradle)

Node.js 18+ & npm / yarn

PostgreSQL 14+

Optional: Docker for DB setup

🔧 Backend Setup (Spring Boot)
# Create Postgres DB
createdb lms

# Or using Docker
docker run --name lms-postgres -e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=lms -p 5432:5432 -d postgres:14


Edit backend/src/main/resources/application.properties:

server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/lms
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
app.jwt.secret=your-secret-key
app.jwt.expiration=86400000


Start the backend:

cd backend
mvn spring-boot:run

💻 Frontend Setup (React + Vite)
cd frontend
npm install
npm run dev


Frontend runs on: http://localhost:5173

Backend runs on: http://localhost:8081

🌐 Environment Variables

Create a .env in frontend/:

VITE_API_BASE=http://localhost:8081/api/lms
VITE_PUBLIC_BASE=http://localhost:8081

🔄 Key Flows
1️⃣ Bank Verification

→ LMS → BMS → Micro-deposit → Verification → Credit Score Update

2️⃣ Loan Application

→ Check verified bank → Calculate EMI → Save as PENDING → Adjust credit score

3️⃣ Admin Approval

→ Fetch BMS snapshot → Approve → LMS disburses loan → EMI schedule generated

4️⃣ Loan Repayment

→ Repayment via BMS → LMS updates EMI & credit score

📡 API Reference
Authentication

POST /login

POST /register

GET /api/lms/profile

PUT /api/lms/updatePassword

Bank APIs

POST /api/lms/bank/verify

POST /api/lms/bank/confirm

Loan APIs

POST /api/lms/loan/apply

GET /api/lms/applications/{accountNumber}

GET /api/lms/active/{accountNumber}

Admin APIs

GET /api/lms/loan/pending

POST /api/lms/loan/approve

POST /api/lms/loan/reject

GET /api/lms/admin/dashboard

Repayment

POST /api/lms/loan/repay

🏦 Bank Management System (BMS)

A demo banking microservice built using Spring Boot that integrates with LMS via JWT-secured APIs.

Features

RESTful APIs for micro-deposits, loan disbursements, and repayments

JWT authentication & ngrok tunneling for secure communication

Handles account verification and transaction tracking

BMS Endpoints
API	Method	Description
/api/bank/hello	GET	Health check
/api/bank/verify	POST	Send micro-deposit
/api/bank/verify-deposit	POST	Confirm micro-deposit
/api/bank/loan	POST	Disburse a loan
/api/bank/repay	POST	Repay loan amount
🗂️ Database Schema (High Level)

users → Stores customer & admin profiles

bank_account → Bank verification status

loan_application → Pending loan requests

loan → Approved loans & EMI details

loan_emi_schedule → EMI tracking per loan

repayment → Loan repayments

credit_score → Customer credit scoring

⚠️ Common Pitfalls & Fixes

CORS Issues → Add Spring CorsConfig

400 on Password Update → Use protected API client

Wrong Dates → Check appliedDate in backend response

🛤 Roadmap

✅ JWT Auth & BMS Integration
✅ Loan Application, Approval, EMI & Repayment
🔄 Multi-bank account support
📨 Email Notifications
📈 Reports & Analytics
🐳 Docker Compose Setup

🤝 Contributing

Fork the repo

Create a feature branch

Commit changes

Open a PR with screenshots & test notes

📄 License

MIT License — free to use, modify & distribute.
