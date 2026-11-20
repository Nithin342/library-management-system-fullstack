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

