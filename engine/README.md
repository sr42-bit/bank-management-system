# 🏦 Core Banking Engine (Full Stack)

A production-style **Core Banking System** built with **Spring Boot (Backend)** and **React (Frontend)** following clean architecture and real-world banking flows.

---

## 🚀 Features

### 👨‍💼 Admin Panel

* Create & manage customers
* View customer details
* Open bank accounts
* View all transactions
* Role-based access control

### 👤 User Panel

* Secure login (JWT आधारित authentication)
* View personal accounts
* Deposit money
* Withdraw money
* Transaction history (credit/debit)
* Real-time balance updates

---

## 🏗️ Architecture

### Backend (Spring Boot)

* Clean Architecture (Hexagonal)
* Layers:

  * Domain (Entities, Value Objects)
  * Application (Use Cases)
  * Infrastructure (JPA, Security)
* JWT Authentication (Stateless)
* Spring Security (Role-based)
* REST APIs

### Frontend (React)

* Component-based architecture
* Protected routes (RBAC)
* Axios API integration
* LocalStorage-based session handling
* Responsive UI (dashboard style)

---

## 🔐 Authentication Flow

1. User logs in → JWT issued
2. JWT contains:

   * email (subject)
   * role
   * customerId
3. Frontend stores token in localStorage
4. Backend validates token on each request
5. Role-based route protection applied

---

## 🗄️ Database Design

### Entities:

* Customer
* User (Auth)
* Account
* Transaction

### Relationships:

Customer → Accounts → Transactions

---

## 🔄 Core Flows

### 1. Admin Creates Customer

Admin → creates customer → system stores profile

### 2. User Registration

User registers using email → password set → linked with customer

### 3. Account Operations

* Deposit → balance increases
* Withdraw → balance decreases
* Transaction recorded

### 4. Transaction Flow

Account → Transaction → stored with:

* fromAccountId
* toAccountId
* type (DEPOSIT / WITHDRAW)
* status
* timestamp

---

## ⚙️ Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* JWT (io.jsonwebtoken)
* JPA / Hibernate
* MySQL

### Frontend

* React
* React Router
* Axios
* CSS (custom styling)

---

## 🧪 API Endpoints (Key)

### Auth

* POST `/api/auth/login`
* POST `/api/auth/register`

### User

* GET `/api/user/accounts`
* POST `/api/user/deposit`
* POST `/api/user/withdraw`
* GET `/api/user/transactions`

### Admin

* GET `/api/admin/customers`
* GET `/api/admin/accounts`
* GET `/api/transactions`

---

## 🛡️ Security

* Stateless JWT authentication
* Role-based access:

  * ROLE_ADMIN
  * ROLE_USER
* Protected frontend routes
* Backend validation

---

## 📦 Setup Instructions

### Backend

```bash
git clone <repo>
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## 📌 Future Improvements

* Transfer money (user → user)
* Pagination for transactions
* Dashboard analytics (charts)
* Refresh token mechanism
* Docker deployment
* CI/CD pipeline (GitHub Actions)

---

## 💡 Key Learnings

* Implemented real-world banking flows
* Applied clean architecture in backend
* Built secure JWT-based authentication
* Designed scalable API structure
* Integrated frontend with protected routing

---

## 👨‍💻 Author

Somesh Rathour
GitHub: https://github.com/sr42-bit
LinkedIn: https://www.linkedin.com/in/someshrathour42

---
