🏦 Loan Management System (LMS)

This repository contains a Loan Management System Web Application built using React (TypeScript) for the frontend and Spring Boot (Java) for the backend, with PostgreSQL as the database.
It integrates with a Bank Management System (BMS) for account verification, loan disbursement, and repayments.

The system supports both customers and admins, providing a seamless way to manage loan applications, approvals, repayments, and account verification.

🚀 Features
For Customers

Register and login using JWT-based authentication

Verify bank accounts via micro-deposit (BMS integration)

Apply for loans with auto-calculated EMI

View active loans, pending applications, and EMI schedules

Make loan repayments online

Update profile and change password securely

For Admins

Dashboard with loan statistics (pending, approved, disbursed, rejected)

Review loan applications with BMS account snapshots

Approve or reject loan applications

Disburse loans and auto-generate EMI schedules

View all loan applications and repayment history

🛠️ Tech Stack

Frontend: React (TypeScript), Vite, Tailwind CSS, Axios

Backend: Spring Boot 3, Java 17, Spring Security, JWT

Database: PostgreSQL

API Integration: BMS Client for verification, disbursement & repayments

Build Tools: Maven

Deployment: Docker (optional)

💻 Running the Project Locally

Follow these steps to set up and run the Loan Management System on your local machine:

1. Clone the Project
git clone https://github.com/your-username/loan-management-system.git
cd loan-management-system

2. Backend Setup (Spring Boot)
a) Create PostgreSQL Database
createdb lms


Or using Docker:

docker run --name lms-postgres -e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=lms -p 5432:5432 -d postgres:14

b) Configure Application Properties

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

c) Start the Backend
cd backend
mvn spring-boot:run


Your backend will run on:
http://localhost:8081

3. Frontend Setup (React + Vite)
a) Configure Environment Variables

Create a .env file inside frontend/:

VITE_API_BASE=http://localhost:8081/api/lms
VITE_PUBLIC_BASE=http://localhost:8081

b) Install & Start the Frontend
cd frontend
npm install
npm run dev


Your frontend will run on:
http://localhost:5173

📁 Project Structure
loan-management-system/
├── backend/
│   ├── src/main/java/com/lms/
│   │   ├── controller/       # REST API controllers
│   │   ├── service/          # Business logic (LoanService, CreditScoreService, etc.)
│   │   ├── model/            # JPA entities
│   │   ├── dto/              # Request & response DTOs
│   │   ├── repository/       # Spring Data JPA repositories
│   │   ├── security/         # JWT authentication & filters
│   │   └── client/           # BMS integration client
│   └── src/main/resources/
│       └── application.properties
└── frontend/
    └── src/
        ├── api/             # Axios API clients
        ├── components/      # Reusable UI components
        ├── contexts/        # Auth context (JWT handling)
        ├── lib/             # API configuration files
        ├── pages/           # Pages (Dashboard, Profile, Loan Applications, etc.)
        └── services/        # Auth & Loan services

📌 Status

🚧 This project is under active development.
New features and improvements are continuously being added. Contributions are always welcome!

📬 Contact

For questions, issues, or contributions, feel free to:

Open an issue

Submit a pull request
