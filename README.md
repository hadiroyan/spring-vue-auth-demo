# Spring Vue Auth Demo

A simple authentication demo project using Spring Boot as backend and Vue.js 3 with vite as frontend.

## Tech Stack

### Backend

- Java 17
- Spring Boot 4.0.1
- Maven

### Frontend

- Vue.js 3
- Vite

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

#### Environment Configuration
```bash
cd backend
cp src/main/resources/application-example.properties src/main/resources/application.properties
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