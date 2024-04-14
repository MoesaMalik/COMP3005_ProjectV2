-- DDL File for Health and Fitness Club Management System

-- Create table for Members
CREATE TABLE Members (
                         member_id SERIAL PRIMARY KEY,
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         email VARCHAR(100) UNIQUE,
                         fitness_goals TEXT,
                         health_metrics TEXT
);

-- Create table for Trainers
CREATE TABLE Trainers (
                          trainer_id SERIAL PRIMARY KEY,
                          first_name VARCHAR(100),
                          last_name VARCHAR(100),
                          available_times tsrange
);

-- Create table for Admins
CREATE TABLE Admins (
                        admin_id SERIAL PRIMARY KEY,
                        first_name VARCHAR(100),
                        last_name VARCHAR(100)
);

-- Create table for Sessions
CREATE TABLE Sessions (
                          session_id SERIAL PRIMARY KEY,
                          member_id INT REFERENCES Members(member_id),
                          trainer_id INT REFERENCES Trainers(trainer_id),
                          session_type VARCHAR(100),
                          room_id INT,
                          session_time TIMESTAMP,
                          status VARCHAR(50) DEFAULT 'scheduled' -- e.g., scheduled, cancelled, completed
);

-- Create table for Rooms
CREATE TABLE Rooms (
                       room_id SERIAL PRIMARY KEY,
                       description VARCHAR(255),
                       capacity INT
);

-- Create table for Equipment
CREATE TABLE Equipment (
                           equipment_id SERIAL PRIMARY KEY,
                           description TEXT,
                           maintenance_schedule DATE
);

-- Create table for Billing (simplified example)
CREATE TABLE Billing (
                         billing_id SERIAL PRIMARY KEY,
                         member_id INT REFERENCES Members(member_id),
                         amount DECIMAL(10, 2),
                         payment_status VARCHAR(50) DEFAULT 'pending' -- e.g., pending, completed, failed
);

-- Create table for RoomBookings
CREATE TABLE RoomBookings (
                              booking_id SERIAL PRIMARY KEY,
                              room_id INT REFERENCES Rooms(room_id),
                              booked_by_admin INT REFERENCES Admins(admin_id),
                              start_time TIMESTAMP,
                              end_time TIMESTAMP,
                              event_description TEXT,
                              status VARCHAR(50) DEFAULT 'scheduled' -- e.g., scheduled, cancelled, completed
);