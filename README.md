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

## Run locally

The backend uses a local H2 database by default, so no database server is required.

1. Start the backend from `Library-Management-System-backend`:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   It listens on `http://localhost:8080`; local database files are stored under the ignored `data` directory.
   For a fresh local database, an initial admin can be created by setting `ADMIN_BOOTSTRAP_ENABLED=true` and a strong `ADMIN_PASSWORD` before starting the backend. Bootstrap is disabled by default; do not enable it in production.
2. In a second terminal, start the frontend from `library-frontend`:

   ```powershell
   npm ci
   npm run dev
   ```

   Open `http://localhost:5173`.

For MySQL, set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` before starting the backend. Set `JWT_SECRET` to a long random value outside local development.

## Deploy on Render

The root `render.yaml` is a Render Blueprint for the API, frontend, and a PostgreSQL database. In Render, create a **Blueprint Instance** from this repository and deploy it. Render supplies the database URL, generated JWT secret, and service URLs; no credentials need to be committed. Existing `DB_URL` settings on an already-configured Render service continue to take precedence over the new database URL.

The Blueprint uses Render's free PostgreSQL plan, which expires after 30 days, and free web services can take about a minute to wake after inactivity. Choose a paid database plan for data that must persist.

