<div align="center">

# 📸 FrameSlot

### *Photography & Videography Studio Booking Platform*

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![AWS](https://img.shields.io/badge/AWS-EC2_+_RDS-FF9900?style=for-the-badge&logo=amazonwebservices&logoColor=white)](https://aws.amazon.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

---

**FrameSlot** connects photography & videography studios with customers through a seamless booking experience. Studio owners register their spaces, define session types, and manage bookings — while customers browse, check availability, and book sessions in real-time.

[🌐 Live API](#-live-deployment) · [📖 API Reference](#-api-reference) · [🚀 Quick Start](#-quick-start) · [🏗️ Architecture](#️-architecture)

</div>

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🔐 Authentication & Security
- JWT-based stateless authentication
- Role-based access control (ADMIN, OWNER, CUSTOMER)
- BCrypt password hashing
- Secure endpoint protection

</td>
<td width="50%">

### 📅 Smart Booking Engine
- Real-time slot availability
- Overlap conflict detection
- Blocked date management
- Max advance booking window

</td>
</tr>
<tr>
<td width="50%">

### 🏢 Studio Management
- Studio registration & profile editing
- Session types (Portrait, Wedding, Event, Product)
- Working hours configuration
- Owner dashboard with analytics

</td>
<td width="50%">

### 👥 Multi-Role System
- **Admin** — Approve/deactivate studios, view all bookings
- **Owner** — Manage studio, confirm/cancel bookings
- **Customer** — Browse studios, book & cancel sessions

</td>
</tr>
</table>

---

## 🌐 Live Deployment

<table>
<tr>
<td>🖥️ <b>Server</b></td>
<td>AWS EC2 (ap-south-1 Mumbai)</td>
</tr>
<tr>
<td>🗄️ <b>Database</b></td>
<td>AWS RDS MySQL 8.0</td>
</tr>
<tr>
<td>🔗 <b>Base URL</b></td>
<td><code>http://15.207.18.220</code></td>
</tr>
<tr>
<td>🩺 <b>Health Check</b></td>
<td><code>http://15.207.18.220/api/auth/login</code></td>
</tr>
</table>

### Quick Test

```bash
# Login with admin credentials
curl -X POST http://15.207.18.220/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@frameslot.local", "password": "admin123"}'
```

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        CLIENT                               │
│              (Postman / Frontend / Mobile)                   │
└──────────────────────┬──────────────────────────────────────┘
                       │  HTTP + JWT Bearer Token
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                   AWS EC2 INSTANCE                          │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              Spring Boot Application                  │  │
│  │                                                       │  │
│  │  ┌─────────┐   ┌──────────┐   ┌──────────────────┐   │  │
│  │  │ Security │──▶│   JWT    │──▶│   Controllers    │   │  │
│  │  │ Filter   │   │ Service  │   │ Auth│Admin│Owner │   │  │
│  │  │ Chain    │   │          │   │     │     │Cust. │   │  │
│  │  └─────────┘   └──────────┘   └────────┬─────────┘   │  │
│  │                                         │             │  │
│  │                              ┌──────────▼─────────┐   │  │
│  │                              │     Services       │   │  │
│  │                              │ Auth│Studio│Booking │   │  │
│  │                              │     │Notif.│Current │   │  │
│  │                              └──────────┬─────────┘   │  │
│  │                                         │             │  │
│  │                              ┌──────────▼─────────┐   │  │
│  │                              │   JPA Repositories │   │  │
│  │                              └──────────┬─────────┘   │  │
│  └─────────────────────────────────────────┼─────────────┘  │
└────────────────────────────────────────────┼────────────────┘
                                             │ JDBC
                                             ▼
                                  ┌──────────────────┐
                                  │   AWS RDS MySQL  │
                                  │   (frameslot)    │
                                  └──────────────────┘
```

---

## 🗃️ Database Schema

```mermaid
erDiagram
    USER ||--o{ STUDIO : owns
    USER ||--o{ BOOKING : books
    STUDIO ||--o{ SESSION_TYPE : offers
    STUDIO ||--o{ WORKING_HOURS : has
    STUDIO ||--o{ BLOCKED_DATE : blocks
    STUDIO ||--o{ BOOKING : receives
    SESSION_TYPE ||--o{ BOOKING : "booked as"
    BOOKING ||--o{ NOTIFICATION : triggers

    USER {
        Long id PK
        String name
        String email UK
        String password
        String phone
        Role role "ADMIN | OWNER | CUSTOMER"
    }

    STUDIO {
        Long id PK
        Long owner_id FK
        String name
        String location
        String bio
        String instagramLink
        StudioStatus status "PENDING | ACTIVE | INACTIVE"
        Instant createdAt
    }

    SESSION_TYPE {
        Long id PK
        Long studio_id FK
        SessionName name "PORTRAIT | WEDDING | EVENT | PRODUCT"
        Integer durationHours
        BigDecimal price
        Integer maxAdvanceDays
        boolean active
    }

    WORKING_HOURS {
        Long id PK
        Long studio_id FK
        DayOfWeek dayOfWeek
        LocalTime openTime
        LocalTime closeTime
    }

    BLOCKED_DATE {
        Long id PK
        Long studio_id FK
        LocalDate date
        String reason
    }

    BOOKING {
        Long id PK
        Long customer_id FK
        Long studio_id FK
        Long session_type_id FK
        LocalDate bookingDate
        LocalTime startTime
        LocalTime endTime
        String eventDetails
        BookingStatus status "PENDING | CONFIRMED | CANCELLED"
        String cancellationReason
        Instant createdAt
    }

    NOTIFICATION {
        Long id PK
        Long booking_id FK
        NotificationType type "CREATED | CONFIRMED | CANCELLED"
        Instant sentAt
    }
```

---

## 📖 API Reference

> **Base URL:** `http://15.207.18.220`
>
> 🔓 = Public &nbsp;&nbsp; 🔐 = Requires JWT Token

### 🔑 Authentication

| Method | Endpoint | Access | Description |
|:------:|----------|:------:|-------------|
| `POST` | `/api/auth/register` | 🔓 | Register a new user |
| `POST` | `/api/auth/login` | 🔓 | Login & receive JWT token |

<details>
<summary><b>POST</b> <code>/api/auth/register</code> — Register a new account</summary>

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepass123",
  "phone": "9876543210",
  "role": "CUSTOMER"
}
```
> `role` must be `CUSTOMER` or `OWNER` (cannot register as `ADMIN`)

**Response** `200 OK`:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 2,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CUSTOMER"
}
```
</details>

<details>
<summary><b>POST</b> <code>/api/auth/login</code> — Login with credentials</summary>

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "securepass123"
}
```

**Response** `200 OK`:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 2,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CUSTOMER"
}
```
</details>

---

### 🏢 Studio Owner Endpoints

> All endpoints require `Authorization: Bearer <token>` with **OWNER** role

| Method | Endpoint | Description |
|:------:|----------|-------------|
| `POST` | `/api/owner/studios` | Register a new studio |
| `PUT` | `/api/owner/studios` | Update studio profile |
| `POST` | `/api/owner/studios/session-types` | Add a session type |
| `POST` | `/api/owner/studios/working-hours` | Set working hours |
| `POST` | `/api/owner/studios/blocked-dates` | Block a date |
| `GET` | `/api/owner/dashboard` | Owner dashboard (stats) |
| `GET` | `/api/owner/bookings` | View all studio bookings |
| `PUT` | `/api/owner/bookings/{id}/confirm` | Confirm a booking |
| `PUT` | `/api/owner/bookings/{id}/cancel` | Cancel a booking |

<details>
<summary><b>POST</b> <code>/api/owner/studios</code> — Register a studio</summary>

**Request Body:**
```json
{
  "name": "Pixel Perfect Studio",
  "location": "Chennai, India",
  "bio": "Professional photography studio specializing in portraits and weddings",
  "instagramLink": "https://instagram.com/pixelperfect"
}
```

**Response** `200 OK`:
```json
{
  "id": 1,
  "ownerId": 2,
  "name": "Pixel Perfect Studio",
  "location": "Chennai, India",
  "bio": "Professional photography studio...",
  "instagramLink": "https://instagram.com/pixelperfect",
  "status": "PENDING"
}
```
</details>

<details>
<summary><b>POST</b> <code>/api/owner/studios/session-types</code> — Add session type</summary>

**Request Body:**
```json
{
  "name": "WEDDING",
  "durationHours": 4,
  "price": 25000.00,
  "maxAdvanceDays": 90
}
```
> `name` options: `PORTRAIT`, `WEDDING`, `EVENT`, `PRODUCT`
</details>

<details>
<summary><b>POST</b> <code>/api/owner/studios/working-hours</code> — Set working hours</summary>

**Request Body:**
```json
{
  "dayOfWeek": "MONDAY",
  "openTime": "09:00",
  "closeTime": "18:00"
}
```
</details>

<details>
<summary><b>POST</b> <code>/api/owner/studios/blocked-dates</code> — Block a date</summary>

**Request Body:**
```json
{
  "date": "2026-07-15",
  "reason": "Studio maintenance"
}
```
</details>

<details>
<summary><b>GET</b> <code>/api/owner/dashboard</code> — Owner dashboard</summary>

**Response** `200 OK`:
```json
{
  "today": 2,
  "thisWeek": 8,
  "thisMonth": 24,
  "upcoming": [
    {
      "id": 1,
      "studioId": 1,
      "studioName": "Pixel Perfect Studio",
      "customerId": 3,
      "customerName": "Jane Smith",
      "customerEmail": "jane@example.com",
      "customerPhone": "9876543210",
      "sessionTypeId": 1,
      "sessionName": "WEDDING",
      "bookingDate": "2026-07-10",
      "startTime": "10:00",
      "endTime": "14:00",
      "eventDetails": "Wedding reception shoot",
      "status": "PENDING",
      "cancellationReason": null
    }
  ]
}
```
</details>

<details>
<summary><b>PUT</b> <code>/api/owner/bookings/{id}/cancel</code> — Cancel a booking</summary>

**Request Body** (optional):
```json
{
  "reason": "Studio unavailable due to emergency"
}
```
</details>

---

### 👤 Customer Endpoints

> Browse endpoints are 🔓 **public**. Booking endpoints require `Authorization: Bearer <token>` with **CUSTOMER** role.

| Method | Endpoint | Access | Description |
|:------:|----------|:------:|-------------|
| `GET` | `/api/customer/studios` | 🔓 | Browse active studios |
| `GET` | `/api/customer/studios/{id}/sessions` | 🔓 | View session types |
| `GET` | `/api/customer/studios/{studioId}/sessions/{sessionTypeId}/slots?date=` | 🔓 | Check available slots |
| `POST` | `/api/customer/bookings` | 🔐 | Create a booking |
| `GET` | `/api/customer/bookings` | 🔐 | View my bookings |
| `PUT` | `/api/customer/bookings/{id}/cancel` | 🔐 | Cancel my booking |

<details>
<summary><b>GET</b> <code>/api/customer/studios/{studioId}/sessions/{sessionTypeId}/slots?date=2026-07-10</code> — Available slots</summary>

**Response** `200 OK`:
```json
{
  "studioId": 1,
  "sessionTypeId": 1,
  "date": "2026-07-10",
  "slots": [
    { "startTime": "00:00", "endTime": "04:00" },
    { "startTime": "04:00", "endTime": "08:00" },
    { "startTime": "08:00", "endTime": "12:00" },
    { "startTime": "16:00", "endTime": "20:00" }
  ]
}
```
</details>

<details>
<summary><b>POST</b> <code>/api/customer/bookings</code> — Create a booking</summary>

**Request Body:**
```json
{
  "studioId": 1,
  "sessionTypeId": 1,
  "bookingDate": "2026-07-10",
  "startTime": "10:00",
  "eventDetails": "Engagement photoshoot for 50 guests"
}
```

**Response** `200 OK`:
```json
{
  "id": 1,
  "studioId": 1,
  "studioName": "Pixel Perfect Studio",
  "customerId": 3,
  "customerName": "Jane Smith",
  "customerEmail": "jane@example.com",
  "customerPhone": "9876543210",
  "sessionTypeId": 1,
  "sessionName": "WEDDING",
  "bookingDate": "2026-07-10",
  "startTime": "10:00",
  "endTime": "14:00",
  "eventDetails": "Engagement photoshoot for 50 guests",
  "status": "PENDING",
  "cancellationReason": null
}
```
</details>

---

### 🛡️ Admin Endpoints

> All endpoints require `Authorization: Bearer <token>` with **ADMIN** role

| Method | Endpoint | Description |
|:------:|----------|-------------|
| `GET` | `/api/admin/studios` | View all studios |
| `PUT` | `/api/admin/studios/{id}/approve` | Approve a pending studio |
| `PUT` | `/api/admin/studios/{id}/deactivate` | Deactivate a studio |
| `GET` | `/api/admin/bookings` | View all platform bookings |

---

## 🔒 Authentication Guide

### How JWT Works in FrameSlot

```
┌──────────┐         ┌──────────────┐         ┌──────────┐
│  Client  │         │  FrameSlot   │         │  MySQL   │
└────┬─────┘         └──────┬───────┘         └────┬─────┘
     │  POST /auth/login    │                      │
     │  {email, password}   │                      │
     │─────────────────────▶│   validate creds     │
     │                      │─────────────────────▶│
     │                      │◀─────────────────────│
     │   {token, userId,    │                      │
     │    name, role}       │                      │
     │◀─────────────────────│                      │
     │                      │                      │
     │  GET /api/owner/...  │                      │
     │  Authorization:      │                      │
     │  Bearer <token>      │   verify JWT         │
     │─────────────────────▶│   extract role       │
     │                      │   check permission   │
     │   200 OK {data}      │                      │
     │◀─────────────────────│                      │
     │                      │                      │
     │  No token / expired  │                      │
     │─────────────────────▶│                      │
     │   401 Unauthorized   │                      │
     │◀─────────────────────│                      │
```

### Using the Token

**In Postman:**
1. Go to the **Authorization** tab
2. Select **Bearer Token**
3. Paste the `token` from the login response

**In curl:**
```bash
curl http://15.207.18.220/api/owner/dashboard \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

### Error Responses

| Status | Meaning | Example |
|:------:|---------|---------|
| `401` | Missing or invalid token | `{"error": "Unauthorized", "message": "You must provide a valid JWT token"}` |
| `403` | Insufficient permissions | `{"error": "Access denied", "message": "You don't have permission"}` |

---

## 📋 Booking Rules

| Rule | Description |
|------|-------------|
| **Overlap Prevention** | No two bookings can overlap in time for the same studio |
| **Blocked Dates** | Studios can block specific dates — no bookings allowed |
| **Advance Window** | Each session type defines a `maxAdvanceDays` limit |
| **Status Flow** | `PENDING` → `CONFIRMED` or `CANCELLED` |
| **Cancellation** | Both customers and owners can cancel with an optional reason |
| **Working Hours** | Informational only — displayed to customers but don't restrict bookings |

---

## 🚀 Quick Start

### Prerequisites

- **Java 17+** — [Download](https://adoptium.net/)
- **Maven 3.8+** — [Download](https://maven.apache.org/)
- **MySQL 8.0+** — [Download](https://dev.mysql.com/downloads/)

### 1. Clone & Configure

```bash
git clone https://github.com/i-saravanan/frameslot.git
cd frameslot
```

### 2. Set Environment Variables

```bash
# Required
export DB_URL="jdbc:mysql://localhost:3306/frameslot?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
export DB_USERNAME="root"
export DB_PASSWORD="your_password"

# Recommended for production
export JWT_SECRET="your-base64-encoded-256-bit-secret"
export ADMIN_PASSWORD="strong-admin-password"
```

<details>
<summary>💻 <b>PowerShell (Windows)</b></summary>

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/frameslot?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="your-base64-encoded-256-bit-secret"
$env:ADMIN_PASSWORD="strong-admin-password"
```
</details>

### 3. Run

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### 4. Default Admin

| Field | Value |
|-------|-------|
| Email | `admin@frameslot.local` |
| Password | `admin123` (or `ADMIN_PASSWORD` env var) |

---

## 🧪 Testing the Complete Flow

Here's a step-by-step walkthrough to test all features:

```bash
BASE=http://15.207.18.220

# ① Register a studio owner
curl -s -X POST $BASE/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Raj Kumar","email":"raj@studio.com","password":"pass123","phone":"9000000001","role":"OWNER"}'
# → Save the "token" as OWNER_TOKEN

# ② Register a customer
curl -s -X POST $BASE/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Priya S","email":"priya@mail.com","password":"pass123","phone":"9000000002","role":"CUSTOMER"}'
# → Save the "token" as CUST_TOKEN

# ③ Owner registers a studio
curl -s -X POST $BASE/api/owner/studios \
  -H "Authorization: Bearer $OWNER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Golden Frame Studio","location":"Chennai","bio":"Premium wedding photography","instagramLink":"https://instagram.com/goldenframe"}'

# ④ Admin approves the studio
curl -s -X POST $BASE/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@frameslot.local","password":"admin123"}'
# → Save the "token" as ADMIN_TOKEN

curl -s -X PUT $BASE/api/admin/studios/1/approve \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# ⑤ Owner adds a session type
curl -s -X POST $BASE/api/owner/studios/session-types \
  -H "Authorization: Bearer $OWNER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"WEDDING","durationHours":4,"price":25000.00,"maxAdvanceDays":90}'

# ⑥ Customer checks available slots
curl -s "$BASE/api/customer/studios/1/sessions/1/slots?date=2026-07-15"

# ⑦ Customer books a slot
curl -s -X POST $BASE/api/customer/bookings \
  -H "Authorization: Bearer $CUST_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"studioId":1,"sessionTypeId":1,"bookingDate":"2026-07-15","startTime":"10:00","eventDetails":"Wedding reception shoot"}'

# ⑧ Owner confirms the booking
curl -s -X PUT $BASE/api/owner/bookings/1/confirm \
  -H "Authorization: Bearer $OWNER_TOKEN"
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.3.5 |
| **Security** | Spring Security + JWT (JJWT 0.12.6) |
| **Database** | MySQL 8.0 (AWS RDS) |
| **ORM** | Spring Data JPA / Hibernate |
| **Validation** | Jakarta Bean Validation |
| **Build** | Maven |
| **Deployment** | AWS EC2 (ap-south-1) |

---

## 📂 Project Structure

```
src/main/java/com/frameslot/
├── FrameSlotApplication.java          # Application entry point
│
├── config/                            # Configuration & Security
│   ├── SecurityConfig.java            # URL rules, JWT filter registration
│   ├── JwtService.java                # Token generation & validation
│   ├── JwtAuthenticationFilter.java   # Per-request JWT validation
│   ├── JwtAuthenticationEntryPoint.java # JSON 401 responses
│   ├── CustomUserDetailsService.java  # Spring UserDetailsService
│   └── DataSeeder.java               # Auto-creates admin user
│
├── domain/                            # JPA Entities & Enums
│   ├── User.java                      # User entity (ADMIN, OWNER, CUSTOMER)
│   ├── Studio.java                    # Photography studio
│   ├── SessionType.java              # Session offerings (Wedding, Portrait...)
│   ├── Booking.java                   # Booking with status lifecycle
│   ├── WorkingHours.java             # Studio operating hours
│   ├── BlockedDate.java              # Dates studio is unavailable
│   ├── Notification.java            # Booking event notifications
│   ├── Role.java                     # OWNER, CUSTOMER, ADMIN
│   ├── BookingStatus.java            # PENDING, CONFIRMED, CANCELLED
│   ├── StudioStatus.java            # PENDING, ACTIVE, INACTIVE
│   ├── SessionName.java             # PORTRAIT, WEDDING, EVENT, PRODUCT
│   └── NotificationType.java        # BOOKING_CREATED, CONFIRMED, CANCELLED
│
├── repository/                        # Spring Data JPA Repositories
│   ├── UserRepository.java
│   ├── StudioRepository.java
│   ├── SessionTypeRepository.java
│   ├── BookingRepository.java
│   ├── WorkingHoursRepository.java
│   ├── BlockedDateRepository.java
│   └── NotificationRepository.java
│
├── service/                           # Business Logic
│   ├── AuthService.java              # Login, Register + JWT generation
│   ├── StudioService.java            # Studio CRUD, slots, dashboard
│   ├── BookingService.java           # Booking lifecycle management
│   ├── CurrentUserService.java       # JWT → User resolution
│   ├── NotificationService.java      # Booking event tracking
│   └── StudioMapper.java            # Entity → Response mapping
│
└── web/                               # REST Controllers & DTOs
    ├── AuthController.java            # /api/auth/*
    ├── AdminController.java           # /api/admin/*
    ├── StudioOwnerController.java     # /api/owner/*
    ├── CustomerController.java        # /api/customer/*
    ├── ApiErrorHandler.java           # Global exception handling
    ├── ApiException.java              # Custom exception class
    └── dto/
        ├── AuthDtos.java              # Login, Register, AuthResponse
        ├── BookingDtos.java           # CreateBookingRequest
        └── StudioDtos.java            # Studio, Session, Booking DTOs
```

---

## 🌍 Deployment (AWS)

FrameSlot is deployed on **AWS** with the following infrastructure:

```
                    ┌───────────────────┐
                    │   Internet        │
                    └────────┬──────────┘
                             │
                    ┌────────▼──────────┐
                    │  Security Group   │
                    │  Port 80, 8080    │
                    └────────┬──────────┘
                             │
                    ┌────────▼──────────┐
                    │    AWS EC2        │
                    │  t2.micro         │
                    │  ap-south-1       │
                    │                   │
                    │  Java 17 + JAR    │
                    └────────┬──────────┘
                             │ JDBC (Port 3306)
                    ┌────────▼──────────┐
                    │    AWS RDS        │
                    │  MySQL 8.0        │
                    │  db.t3.micro      │
                    └───────────────────┘
```

### Build & Deploy

```bash
# Build the JAR
mvn clean package -DskipTests

# Copy to EC2
scp target/frameslot-0.0.1-SNAPSHOT.jar ec2-user@15.207.18.220:~/

# SSH and run
ssh ec2-user@15.207.18.220
nohup java -jar frameslot-0.0.1-SNAPSHOT.jar \
  --DB_URL="jdbc:mysql://<rds-endpoint>:3306/frameslot" \
  --DB_USERNAME="admin" \
  --DB_PASSWORD="<rds-password>" \
  --JWT_SECRET="<production-secret>" \
  --ADMIN_PASSWORD="<strong-password>" &
```

---

## 📝 Environment Variables

| Variable | Required | Default | Description |
|----------|:--------:|---------|-------------|
| `DB_URL` | Yes | `jdbc:mysql://localhost:3306/frameslot` | MySQL connection URL |
| `DB_USERNAME` | Yes | `root` | Database username |
| `DB_PASSWORD` | Yes | — | Database password |
| `JWT_SECRET` | Recommended | (built-in dev key) | Base64-encoded HMAC-SHA256 key |
| `ADMIN_PASSWORD` | Recommended | `admin123` | Admin account password |

---

<div align="center">

### Built with ❤️ using Spring Boot

**[⬆ Back to Top](#-frameslot)**

</div>
