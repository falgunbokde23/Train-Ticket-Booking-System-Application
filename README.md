# Train Ticket Booking System
## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [How to Run the Application](#how-to-run-the-application)
- [Testing the Application](#testing-the-application)
## Project Overview
The **Train Ticket Booking System** is a web application designed to simplify the process of booking and managing train tickets. Users can book tickets, view available seats, and manage their bookings seamlessly. This application aims to enhance the user experience in the travel industry.
## Features
- **User Registration**: Users can register and manage their profiles.
- **Book Tickets**: Users can book tickets by selecting their travel route and available seats.
- **View Tickets**: Users can view their booked tickets and details.
- **Seat Management**: Real-time seat availability tracking for efficient booking.
- **User Management**: Admin features for managing users and bookings.
## Technologies Used
- **Java**: Core programming language
- **Spring Boot**: Framework for building the REST API
- **Spring Data JPA**: For database interaction
- **H2 Database**: In-memory database for easy setup
- **Lombok**: To reduce boilerplate code
- **Jakarta Validation**: For input validation
- **Swagger/OpenAPI**: For API documentation
- **Maven**: Dependency management and project building
## Getting Started
To get a local copy of the project up and running, follow these steps:
### Prerequisites
- Java 11 or higher
- Maven
### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/falgunbokde23/Train-Ticket-Booking-System-Application.git
   cd Train-Ticket-Booking-System-Application
## API Documentation
The API is documented using **Swagger**. You can access it at:
- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [API Specification](http://localhost:8080/v3/api-docs)
## How to Run the Application
After starting the application, you can access it via:
- **Base URL**: `http://localhost:8080/`
### Example API Endpoints
- **Book Ticket**: `POST /ticket/book`
- **Get Ticket by Email**: `GET /ticket/receipt/{email}`
- **Get Available Seats**: `GET /ticket/available-seats`
- **Remove User**: `DELETE /ticket/remove/{email}`
- **Modify User Seat**: `PUT /ticket/modify-seat/{email}`
- **View Users with Seat by Section**: `GET /ticket/section/{section}`

## Testing the Application
- Use **Swagger** or similar tools to test the API endpoints.