<div align="center">

# 📸 FrameSlot

### Photography & Videography Studio Booking Platform

A cloud-deployed Spring Boot REST API for managing photography and videography studio bookings with secure JWT authentication, role-based authorization, real-time booking management, and AWS deployment.

<br>

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![AWS](https://img.shields.io/badge/AWS-EC2_|_RDS_|_Nginx-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)](https://aws.amazon.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

<br>

**Spring Boot • Spring Security • JWT • Hibernate • MySQL • AWS EC2 • Amazon RDS • Nginx • Java 21**

[🚀 Live Demo](#-live-deployment) •
[📖 API Reference](#-api-reference) •
[⚙️ Quick Start](#-quick-start) •
[🏗️ Architecture](#️-deployment-architecture)

</div>

---

# 📖 Overview

FrameSlot is a **full-stack backend application** that enables customers to discover photography studios, view available session types, check real-time availability, and make online bookings.

Studio owners can manage studios, working hours, blocked dates, bookings, and pricing, while administrators control platform approvals and monitor activity.

The project follows a layered Spring Boot architecture using REST APIs, JWT authentication, Spring Security, Spring Data JPA, Hibernate, and MySQL.

The application is deployed on **AWS EC2** with **Amazon RDS MySQL**, uses **Nginx** as a reverse proxy, and is managed by **systemd** for automatic startup and recovery.

---

# ✨ Key Features

## 🔐 Authentication & Security

- JWT Authentication
- Stateless Security
- Spring Security Filter Chain
- BCrypt Password Encryption
- Role-Based Access Control (RBAC)
- Secure REST APIs
- Custom Authentication Entry Point
- Protected API Endpoints

---

## 👥 Multi-Role Access

### 👤 Customer

- Register & Login
- Browse Studios
- View Available Sessions
- Check Slot Availability
- Book Sessions
- Cancel Bookings
- View Booking History

### 📷 Studio Owner

- Register Studio
- Update Studio Profile
- Manage Session Types
- Configure Working Hours
- Block Dates
- View Dashboard
- Confirm Bookings
- Cancel Bookings

### 🛡️ Administrator

- View All Studios
- Approve Studios
- Deactivate Studios
- View Platform Bookings
- Platform Management

---

# 🚀 Core Features

### 📅 Smart Booking System

- Real-time slot availability
- Booking conflict detection
- Prevent overlapping bookings
- Booking status management
- Cancellation support
- Advance booking limits
- Booking history

---

### 🏢 Studio Management

- Studio registration
- Studio approval workflow
- Working hours management
- Blocked date management
- Multiple session types
- Pricing management
- Owner dashboard

---

### 🔔 Booking Workflow

```
Customer
      │
      ▼
Browse Studios
      │
      ▼
View Session Types
      │
      ▼
Check Available Slots
      │
      ▼
Book Session
      │
      ▼
Booking Created
      │
      ▼
Owner Confirms
      │
      ▼
Booking Confirmed
```

---

# 🌐 Live Deployment

| Service | Details |
|---------|---------|
| Cloud Provider | Amazon Web Services (AWS) |
| Compute | Amazon EC2 |
| Database | Amazon RDS MySQL 8 |
| Reverse Proxy | Nginx |
| Service Manager | systemd |
| Java Runtime | Amazon Corretto 21 |
| Build Tool | Maven |
| Region | ap-south-1 (Mumbai) |
| Deployment Type | Production |

### Live API

> **Current Live URL**

```
Available on request
```

---

# 🏗️ Deployment Architecture

```
                        Internet
                            │
                            │
                     HTTP (Port 80)
                            │
                            ▼
                ┌───────────────────────┐
                │        Nginx          │
                │   Reverse Proxy       │
                └──────────┬────────────┘
                           │
                           │
                    localhost:8080
                           │
                           ▼
          ┌────────────────────────────────┐
          │      Spring Boot Application   │
          │                                │
          │ Spring Security                │
          │ JWT Authentication             │
          │ REST Controllers               │
          │ Service Layer                  │
          │ Spring Data JPA                │
          │ Hibernate                      │
          └──────────────┬─────────────────┘
                         │
                         │ JDBC
                         ▼
            ┌──────────────────────────────┐
            │      Amazon RDS MySQL        │
            │                              │
            │        FrameSlot DB          │
            └──────────────────────────────┘
```

---

# 🏛️ System Architecture

```
                 Client Applications
        (Postman / Frontend / Mobile App)
                    │
                    ▼
              REST API Requests
                    │
                    ▼
          Spring Security Filter Chain
                    │
                    ▼
           JWT Authentication Filter
                    │
                    ▼
               REST Controllers
                    │
                    ▼
             Business Services
                    │
                    ▼
          Spring Data JPA Repository
                    │
                    ▼
             Amazon RDS MySQL
```

---

# 🛠️ Technology Stack

| Category | Technology |
|----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.3.5 |
| Security | Spring Security |
| Authentication | JWT |
| ORM | Hibernate |
| Persistence | Spring Data JPA |
| Database | MySQL 8 |
| Build Tool | Maven |
| Server | Apache Tomcat |
| Cloud | AWS EC2 |
| Database Hosting | Amazon RDS |
| Reverse Proxy | Nginx |
| Service Management | systemd |
| API Style | REST |
| IDE | IntelliJ IDEA |
| Version Control | Git & GitHub |

---

# 📊 Project Highlights

- ✅ Java 21
- ✅ Spring Boot 3.3.5
- ✅ Spring Security
- ✅ JWT Authentication
- ✅ Role-Based Authorization
- ✅ RESTful API
- ✅ Hibernate ORM
- ✅ Spring Data JPA
- ✅ Amazon RDS MySQL
- ✅ AWS EC2 Deployment
- ✅ Nginx Reverse Proxy
- ✅ systemd Service
- ✅ Production Deployment
- ✅ Maven Build Automation
- ✅ Layered Architecture
- ✅ Clean Code Principles
- ✅ Exception Handling
- ✅ Validation
- ✅ Secure Password Hashing

---

# 🗄️ Database Schema

The application follows a relational database design with normalized tables to manage users, studios, bookings, session types, working hours, blocked dates, and notifications.

```mermaid
erDiagram

USER ||--o{ STUDIO : owns
USER ||--o{ BOOKING : books

STUDIO ||--o{ SESSION_TYPE : offers
STUDIO ||--o{ WORKING_HOURS : has
STUDIO ||--o{ BLOCKED_DATE : blocks
STUDIO ||--o{ BOOKING : receives

SESSION_TYPE ||--o{ BOOKING : booked_as

BOOKING ||--o{ NOTIFICATION : creates

USER {
Long id PK
String name
String email
String password
String phone
Role role
}

STUDIO {
Long id PK
Long ownerId FK
String name
String location
String bio
String instagramLink
StudioStatus status
}

SESSION_TYPE {
Long id PK
Long studioId FK
SessionName name
Integer durationHours
BigDecimal price
Integer maxAdvanceDays
Boolean active
}

WORKING_HOURS {
Long id PK
Long studioId FK
DayOfWeek dayOfWeek
LocalTime openTime
LocalTime closeTime
}

BLOCKED_DATE {
Long id PK
Long studioId FK
LocalDate date
String reason
}

BOOKING {
Long id PK
Long customerId FK
Long studioId FK
Long sessionTypeId FK
LocalDate bookingDate
LocalTime startTime
LocalTime endTime
BookingStatus status
String cancellationReason
}

NOTIFICATION {
Long id PK
Long bookingId FK
NotificationType type
Instant sentAt
}
```

---

# 🔐 Authentication Flow

FrameSlot uses **JWT (JSON Web Token)** for authentication.

```
               Login Request

      Email + Password
             │
             ▼
      Authentication Manager
             │
             ▼
      Validate Credentials
             │
             ▼
        Generate JWT
             │
             ▼
     Return JWT Token
             │
             ▼
────────────────────────────────────

Every Protected Request

Bearer Token
      │
      ▼
JWT Authentication Filter
      │
      ▼
Validate Token
      │
      ▼
Load User Details
      │
      ▼
Spring Security Context
      │
      ▼
Controller
```

---

# 🔑 User Roles

| Role | Permissions |
|-------|-------------|
| ADMIN | Approve studios, deactivate studios, manage platform |
| OWNER | Manage studios, session types, bookings, working hours |
| CUSTOMER | Browse studios, create bookings, cancel bookings |

---

# 📖 API Reference

## Base URL

```
http://<YOUR-ELASTIC-IP>
```

---

## Authentication APIs

### Register

```http
POST /api/auth/register
```

Request

```json
{
  "name":"John Doe",
  "email":"john@example.com",
  "password":"password123",
  "phone":"9876543210",
  "role":"CUSTOMER"
}
```

Response

```json
{
  "token":"JWT_TOKEN",
  "userId":2,
  "name":"John Doe",
  "email":"john@example.com",
  "role":"CUSTOMER"
}
```

---

### Login

```http
POST /api/auth/login
```

Request

```json
{
  "email":"john@example.com",
  "password":"password123"
}
```

Response

```json
{
  "token":"JWT_TOKEN",
  "userId":2,
  "name":"John Doe",
  "email":"john@example.com",
  "role":"CUSTOMER"
}
```

---

# 👤 Customer APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /api/customer/studios | Browse Studios |
| GET | /api/customer/studios/{id}/sessions | View Session Types |
| GET | /api/customer/studios/{studioId}/sessions/{sessionTypeId}/slots | Check Available Slots |
| POST | /api/customer/bookings | Create Booking |
| GET | /api/customer/bookings | View My Bookings |
| PUT | /api/customer/bookings/{id}/cancel | Cancel Booking |

---

### Create Booking

```http
POST /api/customer/bookings
```

```json
{
  "studioId":1,
  "sessionTypeId":1,
  "bookingDate":"2026-07-10",
  "startTime":"10:00",
  "eventDetails":"Wedding Photography"
}
```

---

# 📸 Studio Owner APIs

| Method | Endpoint |
|---------|----------|
| POST | /api/owner/studios |
| PUT | /api/owner/studios |
| POST | /api/owner/studios/session-types |
| POST | /api/owner/studios/working-hours |
| POST | /api/owner/studios/blocked-dates |
| GET | /api/owner/dashboard |
| GET | /api/owner/bookings |
| PUT | /api/owner/bookings/{id}/confirm |
| PUT | /api/owner/bookings/{id}/cancel |

---

### Register Studio

```http
POST /api/owner/studios
```

```json
{
"name":"Pixel Perfect Studio",
"location":"Chennai",
"bio":"Professional Wedding Photography",
"instagramLink":"https://instagram.com/pixelperfect"
}
```

---

### Add Session Type

```http
POST /api/owner/studios/session-types
```

```json
{
"name":"WEDDING",
"durationHours":4,
"price":25000,
"maxAdvanceDays":90
}
```

---

### Add Working Hours

```http
POST /api/owner/studios/working-hours
```

```json
{
"dayOfWeek":"MONDAY",
"openTime":"09:00",
"closeTime":"18:00"
}
```

---

### Block Date

```http
POST /api/owner/studios/blocked-dates
```

```json
{
"date":"2026-08-15",
"reason":"Maintenance"
}
```

---

# 🛡️ Admin APIs

| Method | Endpoint |
|---------|----------|
| GET | /api/admin/studios |
| PUT | /api/admin/studios/{id}/approve |
| PUT | /api/admin/studios/{id}/deactivate |
| GET | /api/admin/bookings |

---

# 📋 Booking Rules

- Only ACTIVE studios are visible to customers.
- Session types must be active.
- Booking overlaps are automatically prevented.
- Customers cannot exceed the maximum advance booking period.
- Blocked dates cannot be booked.
- Booking lifecycle:

```
PENDING
    │
    ├──────────────► CONFIRMED
    │
    └──────────────► CANCELLED
```

---

# 🔒 JWT Authentication

Every protected endpoint requires:

```
Authorization: Bearer <JWT_TOKEN>
```

Example

```bash
curl http://<YOUR-ELASTIC-IP>/api/owner/dashboard \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

# ❌ Common HTTP Responses

| Status | Description |
|---------|-------------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Resource Not Found |
| 500 | Internal Server Error |

---

# 📁 Project Structure

```
src
├── main
│
├── java
│   └── com.frameslot
│
│       ├── config
│       ├── controller
│       ├── domain
│       ├── repository
│       ├── service
│       ├── security
│       ├── dto
│       ├── mapper
│       ├── exception
│       └── FrameSlotApplication.java
│
├── resources
│
│   ├── application.properties
│   ├── application-prod.properties
│   └── data.sql
│
└── pom.xml
```

---

# 📦 Maven Dependencies

Major libraries used:

- Spring Boot 3.3.5
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL Connector
- JJWT
- Lombok
- Jakarta Validation
- Maven

# 🚀 Quick Start

## Prerequisites

Before running the project, make sure you have:

- Java 21 (Amazon Corretto / OpenJDK)
- Maven 3.9+
- MySQL 8.0+
- Git
- IntelliJ IDEA (Recommended)

---

# 📥 Clone Repository

```bash
git clone https://github.com/i-saravanan/frameslot.git

cd frameslot
```

---

# ⚙️ Configure Environment

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/frameslot

spring.datasource.username=root

spring.datasource.password=your_password

jwt.secret=YOUR_SECRET_KEY
```

or configure environment variables

```bash
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=
ADMIN_PASSWORD=
```

---

# ▶️ Run Application

Using Maven

```bash
mvn spring-boot:run
```

or

```bash
mvn clean package

java -jar target/frameslot-0.0.1-SNAPSHOT.jar
```

Application runs at

```
http://localhost:8080
```

---

# ☁️ AWS Deployment

FrameSlot is deployed completely on AWS.

## Infrastructure

| Service | Configuration |
|----------|---------------|
| Compute | Amazon EC2 |
| Operating System | Amazon Linux 2023 |
| Java Runtime | Amazon Corretto 21 |
| Reverse Proxy | Nginx |
| Database | Amazon RDS MySQL 8 |
| Region | ap-south-1 (Mumbai) |
| Service Manager | systemd |

---

## Production Architecture

```
                    Internet
                        │
                        ▼
               AWS Security Group
                        │
                        ▼
                 HTTP (Port 80)
                        │
                        ▼
                     Nginx
                        │
                        ▼
              Spring Boot Application
                 Port 8080
                        │
                        ▼
                 Amazon RDS MySQL
```

---

## Build

```bash
mvn clean package -DskipTests
```

---

## Upload to EC2

```bash
scp target/frameslot-0.0.1-SNAPSHOT.jar \
ec2-user@<EC2-IP>:~
```

---

## Deploy

Move jar

```bash
sudo mv ~/frameslot-0.0.1-SNAPSHOT.jar /opt/frameslot/
```

Restart service

```bash
sudo systemctl restart frameslot
```

Check status

```bash
sudo systemctl status frameslot
```

View logs

```bash
sudo journalctl -u frameslot -f
```

---

# 🌐 Nginx Reverse Proxy

Nginx forwards all requests to the Spring Boot application.

```
Internet

↓

Nginx (80)

↓

Spring Boot (8080)
```

Benefits

- Reverse Proxy
- Better Security
- Hide Internal Port
- Production Ready
- Easy HTTPS Integration

---

# 🔄 systemd Service

FrameSlot runs as a Linux service.

Useful commands

```bash
sudo systemctl start frameslot

sudo systemctl stop frameslot

sudo systemctl restart frameslot

sudo systemctl status frameslot

sudo journalctl -u frameslot -f
```

---

# 🔒 Security Features

- JWT Authentication
- BCrypt Password Hashing
- Spring Security
- Stateless Authentication
- Role Based Authorization
- Input Validation
- Exception Handling
- Secure Password Storage

---

# 📈 Future Improvements

Planned features

- Email Verification
- Forgot Password
- Refresh Tokens
- Payment Integration
- Studio Reviews
- Image Upload
- Swagger/OpenAPI
- Docker
- Kubernetes
- Redis Cache
- CI/CD Pipeline
- HTTPS with Let's Encrypt
- Custom Domain
- Monitoring with CloudWatch

---

# 🚀 CI/CD (Planned)

GitHub Actions pipeline

```
Developer

↓

Git Push

↓

GitHub Actions

↓

Build

↓

Run Tests

↓

Create Jar

↓

Deploy to EC2

↓

Restart systemd Service
```

---

# 📷 Screenshots

Add screenshots here

```
docs/

├── login.png

├── register.png

├── owner-dashboard.png

├── customer-booking.png

├── admin-panel.png

├── postman.png

├── ec2.png

├── rds.png

└── architecture.png
```

---

# 📊 Project Statistics

| Metric | Count |
|----------|---------|
| Java Version | 21 |
| REST APIs | 20+ |
| Database Tables | 7 |
| User Roles | 3 |
| Booking Workflow | Complete |
| Authentication | JWT |
| Deployment | AWS |
| Database | Amazon RDS |
| Reverse Proxy | Nginx |
| Service Manager | systemd |

---

# 🎯 Learning Outcomes

This project demonstrates practical experience with

- Java Backend Development
- Spring Boot
- Spring Security
- JWT Authentication
- Hibernate
- Spring Data JPA
- REST API Design
- MySQL Database Design
- AWS EC2
- Amazon RDS
- Linux Server Administration
- Nginx Configuration
- systemd Services
- Maven Build Automation
- Git & GitHub
- Production Deployment

---

# 💼 Resume Highlights

**FrameSlot – Photography & Videography Studio Booking Platform**

- Developed a cloud-deployed backend application using Java 21 and Spring Boot.
- Designed secure REST APIs with JWT authentication and Spring Security.
- Implemented role-based authorization for Admin, Studio Owner, and Customer.
- Built a booking engine with availability checking and conflict prevention.
- Integrated Spring Data JPA and Hibernate with Amazon RDS MySQL.
- Deployed the application on AWS EC2 with Nginx reverse proxy and systemd service management.
- Configured Linux server, networking, security groups, and cloud database connectivity.

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository

2. Create a feature branch

```bash
git checkout -b feature-name
```

3. Commit

```bash
git commit -m "Added new feature"
```

4. Push

```bash
git push origin feature-name
```

5. Open a Pull Request

---

# 📄 License

This project is licensed under the **MIT License**.

---

# 👨‍💻 Author

**Saravanan I**

Java Backend Developer

📧 Email: saravanan2004in@gmail.com

🔗 LinkedIn

https://www.linkedin.com/in/i-saravanan/

💻 GitHub

https://github.com/i-saravanan

🌐 Portfolio

https://isaravanan.vercel.app/

---

<div align="center">

## ⭐ If you found this project useful, please consider giving it a Star.

Made with ❤️ by **Saravanan I**

</div>
