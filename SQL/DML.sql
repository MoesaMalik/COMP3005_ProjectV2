-- DML File for Health and Fitness Club Management System

-- Insert data into Members
INSERT INTO Members (first_name, last_name, email, fitness_goals, health_metrics)
VALUES
    ('Bobby', 'Lee', 'john.doe@gmail.com', 'Lose 10 lbs', 'No known issues'),
    ('Jane', 'Smith', 'jane.smith@gmail.com', 'Run a marathon', 'Asthma');

-- Insert data into Trainers
INSERT INTO Trainers (first_name, last_name, available_times)
VALUES
    ('Dwayne', 'Johnson', tsrange('2024-01-01 09:00', '2024-01-01 17:00', '[]')), -- Available from 9 AM to 5 PM
    ('Bobby', 'Lee', tsrange('2024-01-02 10:00', '2024-01-02 16:00', '[]')); -- Available from 10 AM to 4 PM

-- Insert data into Admins
INSERT INTO Admins (first_name, last_name)
VALUES
    ('Charlie', 'Admin'),
    ('Dana', 'Manager');

-- Insert data into Rooms
INSERT INTO Rooms (description, capacity)
VALUES
    ('Yoga Studio', 15),
    ('Weight Room', 20);

-- Insert data into Equipment
INSERT INTO Equipment (description, maintenance_schedule)
VALUES
    ('Treadmill', '2024-12-01'),
    ('Elliptical', '2024-11-01');

-- Insert data into Sessions
INSERT INTO Sessions (member_id, trainer_id, session_type, room_id, session_time, status)
VALUES
    (1, 1, 'Personal Training', 1, '2024-04-15 10:00:00', 'scheduled'),
    (2, 2, 'Yoga Class', 2, '2024-04-16 15:00:00', 'scheduled');

-- Insert data into Billing
INSERT INTO Billing (member_id, amount, payment_status)
VALUES
    (1, 100.00, 'pending'),
    (2, 150.50, 'pending');

-- Insert data into RoomBookings
INSERT INTO RoomBookings (room_id, booked_by_admin, start_time, end_time, event_description, status)
VALUES
    (1, 1, '2024-04-15 09:00:00', '2024-04-15 11:00:00', 'Morning Yoga Session', 'scheduled'),
    (2, 2, '2024-04-16 14:00:00', '2024-04-16 16:00:00', 'Afternoon Training Session', 'scheduled');
