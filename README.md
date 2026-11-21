\# Library Management System



A full-stack Library Management System built using Java Spring Boot, React, and MySQL.



The application provides book, author, member, borrowing, and fine management with JWT-based authentication and role-based authorization.



\## Tech Stack



\### Backend

\- Java 17

\- Spring Boot 4

\- Spring Web

\- Spring Data JPA

\- Spring Security

\- JWT Authentication

\- Jakarta Validation

\- Maven



\### Frontend

\- React

\- Vite

\- JavaScript

\- Axios

\- React Router

\- JWT Decode

\- CSS



\### Database

\- MySQL for local development
\- PostgreSQL for cloud deployments



\## Features



\### Authentication \& Authorization

\- User login with JWT authentication

\- Password encryption using BCrypt

\- Role-based authorization

\- ADMIN and MEMBER roles

\- Protected API endpoints

\- Protected frontend routes



\### Book Management

\- Add books

\- View books

\- Update books

\- Delete books

\- Search books by title

\- Filter books by category

\- Pagination

\- Server-side book pagination with a maximum page size of 100
\- Limit the catalog to 5,000 distinct book records
\- Track total and available quantities

\- Prevent duplicate ISBNs



\### Author Management

\- Add authors

\- View authors

\- Update authors

\- Delete authors

\- View books written by an author



\### Member Management

\- Add members

\- View members

\- Update members

\- Delete members

\- View borrowing history of a member

\- Prevent duplicate member emails



\### Borrowing Management

\- Borrow books

\- Return books

\- Track borrowing and due dates

\- Prevent borrowing when a book is unavailable

\- Prevent duplicate active borrowing

\- View overdue borrowings



\### Fine Management

\- Automatically calculate overdue fines

\- Fine rate: ₹10 per overdue day

\- View fines

\- View fines by member

\- Mark fines as paid

\- Prevent already-paid fines from being paid again



\### Error Handling \& Validation

\- Request validation using Jakarta Validation

\- Custom exceptions

\- Centralized exception handling

\- Meaningful error messages



\## Project Structure



```text

Library-Management-System

│

├── Library-Management-System-backend

│   ├── src

│   │   ├── main

│   │   │   ├── java

│   │   │   │   └── com.library.managament

│   │   │   │       ├── config

│   │   │   │       ├── controller

│   │   │   │       ├── dto

│   │   │   │       ├── entity

│   │   │   │       ├── exception

│   │   │   │       ├── repository

│   │   │   │       └── service

│   │   │   └── resources

│   │   │       └── application.properties

│   │   └── test

│   │

│   └── pom.xml

│

└── library-frontend

&#x20;   ├── public

&#x20;   ├── src

&#x20;   │   ├── components

&#x20;   │   ├── pages

&#x20;   │   ├── services

&#x20;   │   └── styles

&#x20;   ├── package.json

&#x20;   └── vite.config.js

## Run locally on Windows

The backend uses H2 by default; no database server is required. To use a local database instead, create `Library-Management-System-backend/data/application-local.properties` (this file is git-ignored) with your Spring datasource settings. The backend imports it automatically when present.

1. Install Java 17 and Node.js.
2. From the repository root, install frontend dependencies once:

   ```powershell
   cd .\library-frontend
   npm ci
   cd ..
   ```

3. Start both services and open the frontend:

   ```powershell
   .\run-local.ps1
   ```

   The frontend is at `http://localhost:5173` and the backend health endpoint is `http://localhost:8080/health`. The script reuses services already listening on those ports and opens a separate PowerShell window for each service it starts.

For a fresh local database, create the first admin account by sending a `POST` request to `http://localhost:8080/users` with JSON fields `username`, `password`, and `role` set to `ADMIN`. Admin bootstrap is disabled by default; if you enable it explicitly, set a strong `ADMIN_PASSWORD`. Never commit local database credentials or passwords.

## Deploy on Render

The root `render.yaml` is a Render Blueprint for the API, frontend, and a PostgreSQL database. In Render, create a **Blueprint Instance** from this repository and deploy it. Render supplies the database URL, generated JWT secret, and service URLs; no credentials need to be committed. Existing `DB_URL` settings on an already-configured Render service continue to take precedence over the new database URL.

The Blueprint uses Render's free PostgreSQL plan, which expires after 30 days, and free web services can take about a minute to wake after inactivity. Choose a paid database plan for data that must persist.

