# Order Management System 

A comprehensive Java desktop application designed to manage clients, products, and customer orders. The project is built using a strict **3-Tier Architecture**, ensuring a clean separation of concerns between the user interface, business rules, and database operations.

## Architecture & Design Patterns
The application is modularly structured into the following layers:
* **Presentation Layer (`Presentation`):** Graphical User Interface built with Java Swing (`MainView`) to interact with the system.
* **Business Logic Layer (`BLL`):** Contains the core business rules and validation logic. Dedicated validators (e.g., `EmailValidator`, `StockValidator`, `PriceValidator`) ensure data integrity before any database interaction.
* **Data Access Object Layer (`DAO`):** Handles all database CRUD operations. Features an `AbstractDAO` class that leverages **Java Reflection** to dynamically generate SQL queries for any model entity, heavily reducing code duplication.
* **Model Layer (`Model`):** Contains the core entities (`Client`, `Product`, `CustomerOrder`, `Bill`).

## Core Features
* **Client & Product Management:** Add, edit, delete, and view clients and products in the database.
* **Order Processing:** Create customer orders with automatic stock deduction and validation (preventing orders if the requested quantity exceeds available stock).
* **Billing System:** Automatic generation of bills/invoices when an order is successfully processed.
* **Database Connectivity:** Centralized database connection management via the `ConnectionFactory` using JDBC.

## Technologies Used
* **Language:** Java
* **UI Framework:** Java Swing
* **Database:** Relational Database integration via JDBC
* **Core Concepts:** 3-Tier Architecture, DAO Pattern, Java Reflection, Object-Oriented Programming (OOP)
