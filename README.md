# Spring Vue Auth Demo

A simple authentication demo project using Spring Boot as backend and Vue.js 3 with vite as frontend.

## Tech Stack

### Backend

- Java 17
- Spring Boot 4.0.1
- Maven
- PostgreSQL

### Frontend

- Vue.js 3
- Vite
- Tailwind CSS

## Project Structure

```text
spring-vue-auth-demo/
├── backend/          # Spring Boot application
│   ├── src/
│   ├── pom.xml
│   └── mvnw
│
├── frontend/         # Vue.js application (Vite)
│   ├── src/
│   ├── package.json
│   └── vite.config.js
│
└── README.md
```

## Getting Startted

### 1. Backend Setup (Spring Boot)

Prerequisites

- Java 17+
- Maven
- PostgreSQL

#### Environtment Configuration

Create a .env file inside the backend folder, then copy the configuration below and fill in the required values:

```env
# Database
DATABASE_URL=
DATABASE_USER=
DATABASE_PASSWORD=

# OAuth2 - Google
GOOGLE_OAUTH2_CLIENT_ID=
GOOGLE_OAUTH2_CLIENT_SECRET=
```

⚠️ Note:  
Do not commit the .env file to the repository.  
Make sure it is listed in .gitignore.

#### JWT Key Configuration

Generate an RSA private key and public key for Spring Security (JWT signing).

Save the keys in the following directory:

```text
backend/src/main/resources/keys/public.pem
backend/src/main/resources/keys/private.pem
```

#### Run Backend

```bash
mvn spring-boot:run
```

Backend will run at: http://localhost:8080

### 2. Frontend Setup (Vue + vite)

Prerequisites

- Node.js (v20+)
- npm

#### Install Dependencies

```bash
cd frontend
npm install
```

#### Run Frontend

```bash
npm run dev
```

Frontend will run at: http://localhost:5173

## API Endpoints

| Method | Endpoint                       | Description                                     |
| ------ | ------------------------------ | ----------------------------------------------- |
| POST   | `/api/auth/login`              | Authenticate user using username and password   |
| POST   | `/api/auth/register`           | Register a new user with username and password  |
| POST   | `/api/auth/refresh`            | Refresh access token using refresh token        |
| GET    | `/api/auth/me`                 | Get currently authenticated user information    |
| POST   | `/api/auth/logout`             | Logout user and invalidate authentication token |
| GET    | `/api/users/profile`           | Get authenticated user profile details          |
| GET    | `/oauth2/authorization/google` | Redirect user to Google OAuth2 login page       |
