# Trip Booking API

This is a Spring Boot-based RESTful API for managing trips. It supports basic CRUD operations and custom trip creation using Java, Spring Boot, JPA, and MySQL.

## ✅ Technologies Used

* Java
* Spring Boot
* Spring Data JPA
* MySQL
* Hibernate
* REST APIs

## 📌 Features

* Create, read, update, and delete trips
* Distinction between predefined and custom trips
* Uses inheritance with discriminator columns
* Input validation and error handling

## 📂 Structure

* `Trip` (base class with inheritance for different types)
* `CustomTrip`, `PredefinedPackage` (extend Trip)
* Repository & Service layers to handle business logic

## 🛠️ Notes

* Built this project independently to demonstrate proficiency in Spring Boot and full-stack Java backend development.
* Includes object-relational mapping, request validation, error handling, and deployment.

---

*This project is intended for interview demonstration purposes.*
