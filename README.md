# Health and Fitness Club Management System

## Overview

The Health and Fitness Club Management System is a comprehensive platform that caters to the diverse needs of club members, trainers, and administrative staff. It facilitates member registration, profile management, fitness tracking, and session scheduling. Trainers can manage their schedules and view member profiles, while admin staff oversee room bookings, equipment maintenance, class schedules, and process billing and payments.

## Prerequisites
Before running this application, ensure you have the following:

- JDK installed on your system.
- PostgreSQL installed and running on your local machine or network.


## Setting Up
1. **Clone the Repository**: Clone the repository to your local machine using the following command:
   ```bash
   git clone https://github.com/MoesaMalik/COMP3005_ProjectV2.git
   ```
   If you don't have Git installed, you can download the source code files directly from the repository.


2. **Import Database**: Make sure the PostgreSQL database is set up. You can import the database schema using SQL scripts provided separately. You can find the SQL scripts in the `SQL` directory of this repository. To import the database schema, follow these steps:
   - Open your PostgreSQL database management tool (e.g., pgAdmin).
   - Create a new database.
   - Open the SQL script file (e.g., `DDL.sql`) using a text editor.
   - Execute the SQL commands in the script to create the necessary tables and schema in your PostgreSQL database.


3. **Configure Database Connection**: Open the `HealthClubSystem.java` file in your preferred IDE and update the following constants according to your PostgreSQL server configuration:
   - `URL`: The JDBC URL for connecting to your PostgreSQL server. It should be in the format `jdbc:postgresql://<hostname>:<port>/<database_name>`.
   - `USER`: Your PostgreSQL username.
   - `PASSWORD`: Your PostgreSQL password.

Once you've completed these steps, your environment should be set up and ready to use the application.

## Features

### Member Functions
- **User Registration**: Members can sign up and create their personal profile.
- **Profile Management**: Members can update their personal information, set fitness goals, and record health metrics.
- **Dashboard Display**: Members can view their exercise routines, fitness achievements, and health statistics.
- **Schedule Management**: Allows members to book, reschedule, or cancel training sessions and classes.

### Trainer Functions
- **Schedule Management**: Trainers set their availability for sessions.
- **Member Profile Viewing**: Trainers can view profiles of members including fitness goals and health metrics.

### Administrative Staff Functions
- **Room Booking Management**: Admins can schedule rooms for sessions and events.
- **Equipment Maintenance Monitoring**: Oversee the maintenance of gym equipment.
- **Class Schedule Updating**: Manage the timing and scheduling of fitness classes.
- **Billing and Payment Processing**: Handle billing records and process payments.

## Database Design

The system is built on a relational database with the following tables and relationships:

- **Members**: Stores member information and is linked to Billing and Sessions.
- **Trainers**: Contains trainer details and is linked to Sessions.
- **Admins**: Holds admin staff profiles and is linked to RoomBookings.
- **Sessions**: Records details of member bookings and which trainers are conducting them.
- **Rooms**: Lists rooms available for sessions.
- **Equipment**: Details of gym equipment and maintenance schedules.
- **Billing**: Financial records associated with each member.
- **RoomBookings**: Details of room reservations made by admins.

## ER Diagram

![er_diagram.png](/Documentation/er_diagram.png)


## Database Schema

![relation_schema.png](/Documentation/relation_schema.png)
