Loan Management System (LMS)
React + Spring Boot + PostgreSQL + BMS Integration

A full-stack Loan Management System built with React (TypeScript) on the frontend and Spring Boot on the backend,
fully integrated with a Bank Management System (BMS) for account verification, loan disbursement, and repayments.
📑 Table of Contents

✨ Features

🛠️ Tech Stack

🏗️ Architecture

📂 Project Structure

⚡ Prerequisites

🚀 Setup & Installation

Backend Setup

Frontend Setup

🔑 Environment Variables

🔄 Key Flows

📡 API Reference

🎨 Frontend Integration

🗄️ Database Schema

⚠️ Common Pitfalls & Fixes

🛣️ Roadmap

🤝 Contributing

📄 License

✨ Features
For Customers

🔹 Secure JWT-based authentication (Register & Login)

🔹 Bank account verification via micro-deposit BMS integration

🔹 Apply for loans with auto-calculated EMI

🔹 View active loans, loan applications, and EMI schedules

🔹 Make repayments directly via LMS

🔹 Update passwords with old password verification

For Admins

📊 Admin Dashboard — statistics on loans, applications, and disbursements

🔍 Review pending applications with BMS account snapshots

✅ Approve or ❌ Reject loan applications

💳 Auto-disburse loans with BMS integration

📝 View all applications & loan summaries

🛠️ Tech Stack
Category	Technology
Frontend	React (TypeScript), Vite, Axios, Tailwind CSS
Backend	Spring Boot 3, Java 17, Spring Security, JWT
Database	PostgreSQL 14+
API Client	Axios (public & protected APIs)
Deployment	Docker (optional), Vercel/Netlify (frontend)
Bank System	BMS Client (account verification, loan disbursement, repayments)
🏗️ Architecture
flowchart LR
  A[React + Vite (Frontend)] -->|Axios| B[Spring Boot API (/api/lms)]
  B --> C[AuthService / JWT]
  B --> D[LoanService]
  B --> E[CreditScoreService]
  B --> F[MailService]
  B --> G[BMS Client (Bank Integration)]
  D --> H[(PostgreSQL Database)]
  C --> H
  E --> H
  D -->|Disburse / Repay / Verify| G

📂 Project Structure
lms/
├── backend/
│   ├── src/main/java/com/lms/
│   │   ├── controller/      # REST API endpoints
│   │   ├── service/         # Business logic
│   │   ├── model/           # JPA entities
│   │   ├── dto/             # Request & Response DTOs
│   │   ├── repository/      # Spring Data JPA repositories
│   │   ├── security/        # JWT configs, filters, authentication
│   │   └── client/          # BMS client integration
│   └── src/main/resources/
│       └── application.properties
└── frontend/
    └── src/
        ├── api/            # Axios API clients
        ├── components/     # Reusable UI components
        ├── contexts/       # AuthContext
        ├── lib/            # Axios instances (api & publicApi)
        ├── pages/          # Pages (Admin, Profile, Dashboard, etc.)
        └── services/       # Auth & Loan services

⚡ Prerequisites

Java 17+

Maven or Gradle

Node.js 18+

PostgreSQL 14+

Docker (optional)

🚀 Setup & Installation
1. Backend Setup (Spring Boot)
# Clone the repository
git clone https://github.com/your-username/lms.git
cd lms/backend

# Create PostgreSQL DB
createdb lms

# Or via Docker
docker run --name lms-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=lms -p 5432:5432 -d postgres:14

# Update application.properties
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/lms
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update

# Run the backend
mvn spring-boot:run


Backend will run at: http://localhost:8081

2. Frontend Setup (React + Vite)
cd lms/frontend
npm install
npm run dev


Frontend will run at: http://localhost:5173

🔑 Environment Variables

Create a .env file in frontend/:

VITE_API_BASE=http://localhost:8081/api/lms
VITE_PUBLIC_BASE=http://localhost:8081

🔄 Key Flows

Bank Verification (via BMS)

LMS sends a micro-deposit → waits for confirmation

User enters deposited amount → account marked VERIFIED

Apply for Loan

User must have verified account

EMI auto-calculated → status = PENDING

Admin Approval

Admin checks application + BMS snapshot

Approve → LMS calls BMS.disburseLoan()

EMI schedule auto-generated

Loan Repayment

User pays → LMS calls BMS.repayLoan()

Updates loan status, EMIs, and credit score

📡 API Reference
Endpoint	Method	Description
/register	POST	Register a new user
/login	POST	Login and receive JWT
/api/lms/profile	GET	Get current user profile
/api/lms/bank/verify	POST	Initiate bank verification
/api/lms/bank/confirm	POST	Confirm micro-deposit
/api/lms/loan/apply	POST	Apply for a loan
/api/lms/loan/pending	GET	Get pending loan applications
/api/lms/loan/approve	POST	Approve loan
/api/lms/loan/reject	POST	Reject loan
/api/lms/loan/repay	POST	Repay loan EMI
🗄️ Database Schema

users → id, username, email, password_hash, role, last_login

bank_account → id, user_id, account_number, status

loan_application → id, user_id, loan_amount, purpose, term_months, status

loan → id, user_id, total_loan, remaining_amount, emi_amount

loan_emi_schedule → id, loan_id, emi_amount, principal, interest, due_date

repayment → id, loan_id, amount, repayment_date, status

credit_score → id, user_id, score

⚠️ Common Pitfalls & Fixes

CORS Issues → Add CorsConfig.java to whitelist React URL.

400 on Update Password → Ensure correct old password validation.

Date Display Issues → Send proper timestamps from backend.

Numbers as Strings → Frontend handles both strings and numbers.

🛣️ Roadmap

✅ JWT Auth + BMS Integration

✅ Loan Applications + EMI Calculations

✅ Admin Dashboard + Approval Flow

🔄 Role-based Method Security

📩 Email Notifications

📊 Analytics & Reports

🧪 Unit & Integration Tests

🐳 Docker Compose for Full Stack

🤝 Contributing

Fork the repo

Create a feature branch

Commit your changes

Open a Pull Request

📄 License

This project is licensed under the MIT License — you are free to use, modify, and distribute it.
