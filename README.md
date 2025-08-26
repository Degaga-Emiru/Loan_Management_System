Loan Management System (LMS) — React + Spring Boot (with BMS integration)

A full-stack Loan Management System built with React (TypeScript) on the front end and Spring Boot (Java) on the back end, integrated with a lightweight Bank Management System (BMS) client for account verification, loan disbursement, and repayments.

This repo is structured so you can clone, run locally, and extend for production.

Table of contents

Features

Architecture

Monorepo structure

Prerequisites

Backend setup (Spring Boot)

Frontend setup (React + Vite)

Environment variables

Key flows

API reference

Frontend integration details

Database schema (high level)

Common pitfalls & fixes

Roadmap

Contributing

License

Features
End-user (customer)

Register, login (JWT-based)

Bank account verification via micro-deposit (BMS client)

Apply for a loan (EMI auto-calculated)

View active loans, applications, and EMI schedule

Repay loan amounts

Update password (with old password check)

Admin

Dashboard (stats: total apps, pending, approved, total disbursed)

Pending applications review with BMS snapshot (paid/remaining/total)

Approve & disburse loans (auto generates EMI schedule)

Reject applications

See all loan applications / loans summary

Architecture
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
  D -->|disburse / repay / verify| G


React app (TypeScript) talks to:

publicApi (no auth) for /login, /register

api (JWT) for /api/lms/**

Spring Boot exposes REST endpoints, issues/validates JWT, calculates EMI, and calls BMS client (stub or real integration) to:

verify accounts (micro-deposit)

disburse loans

receive repayments

PostgreSQL stores users, bank accounts, applications, loans, EMI schedule, repayments, credit score

Monorepo structure
.
├─ backend/
│  ├─ src/main/java/com/LMS/LMS/
│  │  ├─ Controller/           # REST endpoints
│  │  ├─ Service/              # Business logic (LoanService, CreditScoreService, MailService)
│  │  ├─ Model/                # JPA entities (Users, BankAccount, LoanApplication, Loan, LoanEmiSchedule, Repayment, CreditScore)
│  │  ├─ DTO/                  # Response/Request DTOs (LoanAppDto, LoanDTO, PendingLoanResponseDto, LoanAdminResponseDto, etc.)
│  │  ├─ Reppo/                # Spring Data repositories
│  │  ├─ Security/             # JWT config, filters, auth
│  │  └─ Client/               # BmsClient (bank integration)
│  └─ src/main/resources/
│     └─ application.properties
└─ frontend/
   └─ src/
      ├─ api/                  # adminLoanApi.ts, loanApi.ts
      ├─ components/           # UI components (ChangePasswordModal, etc.)
      ├─ contexts/             # AuthContext (login, token, user profile)
      ├─ lib/                  # axios clients: api (JWT, /api/lms), publicApi (no auth)
      ├─ pages/                # AdminDashboard, AdminApplications, Profile
      └─ services/             # auth.ts (login/register/loadMe/updatePassword)

Prerequisites

Java 17+

Maven (or Gradle) — sample commands use Maven

Node.js 18+ and npm (or pnpm/yarn)

PostgreSQL 14+ (or change JDBC URL to your DB)

Optional: Docker for Postgres

Backend setup (Spring Boot)

Create a Postgres DB (example):

createdb lms


Or with Docker:

docker run --name lms-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=lms -p 5432:5432 -d postgres:14


Configure backend/src/main/resources/application.properties:

server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5432/lms
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
app.jwt.secret=change-this-super-secret
app.jwt.expiration=86400000

# (Optional) email
spring.mail.host=smtp.example.com
spring.mail.username=...
spring.mail.password=...


Run backend:

cd backend
mvn spring-boot:run


It should start on http://localhost:8081
 and expose protected APIs under /api/lms/**.

Frontend setup (React + Vite)

Configure axios base URLs

frontend/src/lib/api.ts (protected with JWT):

import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8081/api/lms",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export default api;


frontend/src/lib/publicApi.ts (public, no JWT):

import axios from "axios";

const publicApi = axios.create({
  baseURL: "http://localhost:8081",
});

export default publicApi;


Install & run:

cd frontend
npm install
npm run dev


The app should open on http://localhost:5173
 (or similar).

Environment variables

You can also drive base URLs from a .env in frontend/:

# frontend/.env
VITE_API_BASE=http://localhost:8081/api/lms
VITE_PUBLIC_BASE=http://localhost:8081


Then use them in the axios clients instead of hardcoding.

Key flows
1) Bank verification (via BMS)

Send account to BMS: user submits account number → LMS invokes BmsClient.verifyAccount() (simulates micro-deposit → WAITING_VERIFICATION)

Confirm micro-deposit: user enters deposit amount → LMS calls BmsClient.confirmMicroDeposit() → marks account VERIFIED and adjusts credit score.

2) Apply for a loan

User must have VERIFIED bank account and no outstanding BMS debt.

EMI is calculated (10% annual interest by default).

Application saved as PENDING and credit score adjusted slightly.

3) Admin review & approve

Admin opens Pending Review: each row shows a BMS snapshot (paid/remaining/total).

If outstanding exists → auto-reject on approval attempt (and notify).

On approval → LMS calls BmsClient.disburseLoan(), creates Loan, generates EMI schedule, updates credit score.

4) Repayment

User posts a repayment → LMS calls BmsClient.repayLoan() → updates Repayment, re-apportions EMI interest/principal, recalculates future EMIs if needed, updates credit score.

API reference

Prefix for protected endpoints: /api/lms

Auth

POST /login — returns JWT (in header Authorization: Bearer ... or body)

POST /register

GET /api/lms/profile — current user (requires token)

PUT /api/lms/updatePassword — body: { oldPassword, newPassword }

Bank (names may vary depending on your controller)

POST /api/lms/bank/verify — body: { accountNumber } (sends micro-deposit, returns WAITING)

POST /api/lms/bank/confirm — body: { accountNumber, amount } (verifies)

Loan (customer)

POST /api/lms/loan/apply — body: { accountNumber, amount, purpose, termMonths }

GET /api/lms/applications/{accountNumber} — your applications

GET /api/lms/active/{accountNumber} — active loans

Loan (admin)

GET /api/lms/loan/pending — list of PendingLoanResponseDto (application + BMS summary)

POST /api/lms/loan/approve?loanApplicationId={id}

POST /api/lms/loan/reject?loanApplicationId={id}

Admin

GET /api/lms/admin/applications — all applications (optional)

GET /api/lms/admin/dashboard — stats

Repayment

POST /api/lms/loan/repay — body: { accountNumber, amount }

TIP: Use the browser Network tab or curl to confirm exact shapes during dev.

Frontend integration details

AuthContext provides login, logout, updatePassword, user, loading.

Axios clients

publicApi → /login, /register

api → adds Authorization: Bearer <token> (interceptor)

Services

services/auth.ts → login, register, loadMe, logout, updatePassword

api/adminLoanApi.ts → fetchPendingForAdmin, approveApplication, rejectApplication

api/loanApi.ts → maps backend loan/app data → strict UI types

UI pages

pages/admin/AdminApplications.tsx — pending review table with Approve/Reject

pages/admin/AdminDashboard.tsx — stats + applications (you can comment/uncomment Applied column)

pages/profile/Profile.tsx — personal info + Change Password modal

Database schema (high level)

users (id, username, email, password_hash, role, created_at, last_login, …)

bank_account (id, user_id, account_number, status [WAITING_VERIFICATION|VERIFIED], retry_count)

loan_application (id, user_id, account_id, loan_amount, purpose, term_months, status [PENDING|APPROVED|REJECTED], emi_per_month, total_emi)

loan (id, user_id, account_id, total_loan, remaining_amount, term_months, loan_date, due_date, emi_amount)

loan_emi_schedule (id, loan_id, installment_number, emi_amount, principal_component, interest_component, remaining_principal, due_date, status)

repayment (id, loan_id, amount, repayment_date, status [WAITING|PAID|FAILED])

credit_score (id, user_id, score)

Use Flyway/Liquibase for reproducible migrations in production.

Common pitfalls & fixes

400 on /updatePassword

Make sure services/auth.ts uses the protected api client (baseURL /api/lms) — not publicApi.

If old password is wrong, backend intentionally returns 400 ("❌ Incorrect old password"). Surface the error message in UI.

CORS issues
Add a simple CORS config in Spring (dev only):

@Configuration
public class CorsConfig {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          .allowedOrigins("http://localhost:5173")
          .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
          .allowCredentials(true);
      }
    };
  }
}


Dates showing 1969 or —

That happens when a date field is missing or parsed as epoch 0.

Either send a real appliedDate from backend, or comment out the column (we included a commented version you can re-enable later).

Numbers arrive as strings

The frontend mappers (toNum) already handle string | number | null.

Roadmap

✅ JWT auth, bank verification, loan apply/approve/reject, EMI schedule, repay

🔒 Role-based authorization polish (method security)

📨 Real email notifications

📈 Reports/analytics (per user/per product)

💳 Multiple bank accounts per user

🧪 Full test coverage (unit/integration/e2e)

🐳 Docker Compose for full stack

Contributing

Fork & clone

Create a feature branch

Commit with meaningful messages

Open a PR with context, screenshots for UI changes, and test notes

License

MIT — feel free to use and modify. Attribution appreciated!

Quick test with cURL
# Register
curl -X POST http://localhost:8081/register \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Alice","username":"alice","email":"alice@example.com","password":"Secret123!"}'

# Login (note: token may be in header or body depending on your backend)
curl -i -X POST http://localhost:8081/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"alice","password":"Secret123!"}'

# Use token
TOKEN="YOUR_JWT_HERE"
curl -H "Authorization: Bearer $TOKEN" http://localhost:8081/api/lms/profile
