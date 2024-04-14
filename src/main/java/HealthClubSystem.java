import java.sql.*;
import java.util.Scanner;

public class HealthClubSystem {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/healthclub";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "admin";
    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
            showMainMenu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to the Health and Fitness Club Management System");
            System.out.println("1. Member Menu");
            System.out.println("2. Trainer Menu");
            System.out.println("3. Admin Menu");
            System.out.println("4. Exit");
            System.out.print("Select your role: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    memberMenu();
                    break;
                case "2":
                    trainerMenu();
                    break;
                case "3":
                    adminMenu();
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void memberMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMember Menu");
            System.out.println("1. Register");
            System.out.println("2. Update Profile");
            System.out.println("3. View Dashboard");
            System.out.println("4. Schedule Session");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerMember();
                    break;
                case "2":
                    updateMemberProfile();
                    break;
                case "3":
                    viewMemberDashboard();
                    break;
                case "4":
                    scheduleSession();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void trainerMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nTrainer Menu");
            System.out.println("1. Set Availability");
            System.out.println("2. View Member Profile");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    setTrainerAvailability();
                    break;
                case "2":
                    viewMemberProfile();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Manage Room Bookings");
            System.out.println("2. Monitor Equipment Maintenance");
            System.out.println("3. Update Class Schedules");
            System.out.println("4. Process Billing");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manageRoomBookings();
                    break;
                case "2":
                    monitorEquipmentMaintenance();
                    break;
                case "3":
                    updateClassSchedules();
                    break;
                case "4":
                    processBilling();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //Members
    private static void registerMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter fitness goals: ");
        String fitnessGoals = scanner.nextLine();
        System.out.print("Enter health metrics: ");
        String healthMetrics = scanner.nextLine();

        String sql = "INSERT INTO Members (first_name, last_name, email, fitness_goals, health_metrics) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, fitnessGoals);
            pstmt.setString(5, healthMetrics);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Member registered successfully.");
            } else {
                System.out.println("Member registration failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during member registration: " + e.getMessage());
        }
    }

    private static void updateMemberProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your member ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());  
        System.out.print("Update fitness goals: ");
        String fitnessGoals = scanner.nextLine();
        System.out.print("Update health metrics: ");
        String healthMetrics = scanner.nextLine();

        String sql = "UPDATE Members SET fitness_goals = ?, health_metrics = ? WHERE member_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fitnessGoals);
            pstmt.setString(2, healthMetrics);
            pstmt.setInt(3, memberId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Profile updated successfully.");
            } else {
                System.out.println("Profile update failed or no changes made.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during profile update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void viewMemberDashboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your member ID to view dashboard: ");
        int memberId = Integer.parseInt(scanner.nextLine());

       
        try (PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM Members WHERE member_id = ?")) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nMember Dashboard:");
                    System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Fitness Goals: " + rs.getString("fitness_goals"));
                    System.out.println("Health Metrics: " + rs.getString("health_metrics"));
                } else {
                    System.out.println("No member found with ID " + memberId);
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving member information: " + e.getMessage());
            e.printStackTrace();
        }

        
        try (PreparedStatement pstmt = connection.prepareStatement(
                "SELECT session_type, room_id, session_time, status FROM Sessions WHERE member_id = ? ORDER BY session_time DESC")) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\nScheduled Sessions:");
                while (rs.next()) {
                    System.out.println("Session Type: " + rs.getString("session_type") +
                            ", Room ID: " + rs.getInt("room_id") +
                            ", Session Time: " + rs.getTimestamp("session_time") +
                            ", Status: " + rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving session information: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void scheduleSession() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Schedule a New Training Session");

        System.out.print("Enter Member ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Trainer ID: ");
        int trainerId = Integer.parseInt(scanner.nextLine());


        if (!displayAvailableTimes(trainerId)) {
            return;
        }

        System.out.print("Enter Session Type (e.g., Yoga, Cardio, Strength): ");
        String sessionType = scanner.nextLine();
        System.out.print("Enter Room ID: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Session Date and Time (YYYY-MM-DD HH:MM): ");
        String sessionDateTime = scanner.nextLine();
        Timestamp sessionTime = Timestamp.valueOf(sessionDateTime);


        String sql = "INSERT INTO Sessions (member_id, trainer_id, session_type, room_id, session_time, status) VALUES (?, ?, ?, ?, ?, 'scheduled')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, trainerId);
            pstmt.setString(3, sessionType);
            pstmt.setInt(4, roomId);
            pstmt.setTimestamp(5, sessionTime);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Session scheduled successfully.");
            } else {
                System.out.println("Failed to schedule session.");
            }
        } catch (SQLException e) {
            System.out.println("Error scheduling session: " + e.getMessage());
        }
    }

    private static boolean displayAvailableTimes(int trainerId) {

        String sql = "SELECT available_times FROM Trainers WHERE trainer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                String range = rs.getString("available_times");
                System.out.println("Trainer's available times: " + range);
                return true;
            } else {
                System.out.println("No available times found for trainer.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching trainer availability: " + e.getMessage());
            return false;
        }
    }


    private static boolean isTrainerAvailable(int trainerId, Timestamp sessionTime) {
        String sql = "SELECT COUNT(*) FROM Trainers WHERE trainer_id = ? AND available_times @> ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setTimestamp(2, sessionTime);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking trainer availability: " + e.getMessage());
        }
        return false;
    }


    //TRAINER
    private static void setTrainerAvailability() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Trainer ID: ");
        int trainerId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter your new availability start time (YYYY-MM-DD HH:MM:SS): ");
        String start = scanner.nextLine();
        System.out.print("Enter your new availability end time (YYYY-MM-DD HH:MM:SS): ");
        String end = scanner.nextLine();

        String sql = "UPDATE Trainers SET available_times = tsrange(?, ?) WHERE trainer_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(start));
            pstmt.setTimestamp(2, Timestamp.valueOf(end));
            pstmt.setInt(3, trainerId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Availability updated successfully.");
            } else {
                System.out.println("Failed to update availability.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating availability: " + e.getMessage());
        }
    }
    private static void viewMemberProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Member's First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Member's Last Name: ");
        String lastName = scanner.nextLine();

        String sql = "SELECT * FROM Members WHERE first_name = ? AND last_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Member ID: " + rs.getInt("member_id"));
                System.out.println("First Name: " + rs.getString("first_name"));
                System.out.println("Last Name: " + rs.getString("last_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Fitness Goals: " + rs.getString("fitness_goals"));
                System.out.println("Health Metrics: " + rs.getString("health_metrics"));
            } else {
                System.out.println("No member found with the name " + firstName + " " + lastName);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing member profile: " + e.getMessage());
        }
    }


    //ADMIN

    public static void manageRoomBookings() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nRoom Booking Management");
            System.out.println("1. Add Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. Update Booking");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Return to Main Menu");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBooking();
                    break;
                case "2":
                    viewBookings();
                    break;
                case "3":
                    updateBooking();
                    break;
                case "4":
                    cancelBooking();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void addBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Room ID: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Admin ID who is booking: ");
        int adminId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Start Time (YYYY-MM-DD HH:MM:SS): ");
        Timestamp startTime = Timestamp.valueOf(scanner.nextLine());
        System.out.print("Enter End Time (YYYY-MM-DD HH:MM:SS): ");
        Timestamp endTime = Timestamp.valueOf(scanner.nextLine());
        System.out.print("Enter Event Description: ");
        String description = scanner.nextLine();

        String sql = "INSERT INTO RoomBookings (room_id, booked_by_admin, start_time, end_time, event_description, status) VALUES (?, ?, ?, ?, ?, 'scheduled')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, adminId);
            pstmt.setTimestamp(3, startTime);
            pstmt.setTimestamp(4, endTime);
            pstmt.setString(5, description);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Booking added successfully.");
            } else {
                System.out.println("Failed to add booking.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void viewBookings() {
        String sql = "SELECT * FROM RoomBookings";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id") +
                        ", Room ID: " + rs.getInt("room_id") +
                        ", Admin ID: " + rs.getInt("booked_by_admin") +
                        ", Start Time: " + rs.getTimestamp("start_time") +
                        ", End Time: " + rs.getTimestamp("end_time") +
                        ", Description: " + rs.getString("event_description") +
                        ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void updateBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Booking ID to update: ");
        int bookingId = Integer.parseInt(scanner.nextLine());
        System.out.print("Update Room ID (leave blank to not update): ");
        String roomId = scanner.nextLine();
        System.out.print("Update Start Time (YYYY-MM-DD HH:MM:SS) (leave blank to not update): ");
        String startTime = scanner.nextLine();
        System.out.print("Update End Time (YYYY-MM-DD HH:MM:SS) (leave blank to not update): ");
        String endTime = scanner.nextLine();
        System.out.print("Update Event Description (leave blank to not update): ");
        String description = scanner.nextLine();
        System.out.print("Update Status (scheduled, cancelled, completed) (leave blank to not update): ");
        String status = scanner.nextLine();

        try {
            StringBuilder sqlBuilder = new StringBuilder("UPDATE RoomBookings SET ");
            boolean first = true;

            if (!roomId.isEmpty()) {
                sqlBuilder.append("room_id = ").append(Integer.parseInt(roomId));
                first = false;
            }
            if (!startTime.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("start_time = '").append(Timestamp.valueOf(startTime)).append("'");
                first = false;
            }
            if (!endTime.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("end_time = '").append(Timestamp.valueOf(endTime)).append("'");
                first = false;
            }
            if (!description.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("event_description = '").append(description).append("'");
                first = false;
            }
            if (!status.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("status = '").append(status).append("'");
            }
            sqlBuilder.append(" WHERE booking_id = ").append(bookingId);

            try (PreparedStatement pstmt = connection.prepareStatement(sqlBuilder.toString())) {
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Booking updated successfully.");
                } else {
                    System.out.println("Failed to update booking. Please check if the booking ID is correct.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = Integer.parseInt(scanner.nextLine());

        String sql = "UPDATE RoomBookings SET status = 'cancelled' WHERE booking_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Booking cancelled successfully.");
            } else {
                System.out.println("Failed to cancel booking. Please check if the booking ID is correct.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void monitorEquipmentMaintenance() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEquipment Maintenance Monitoring");
            System.out.println("1. Add Maintenance Record");
            System.out.println("2. View Maintenance Schedules");
            System.out.println("3. Update Maintenance Record");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addMaintenanceRecord();
                    break;
                case "2":
                    viewMaintenanceSchedules();
                    break;
                case "3":
                    updateMaintenanceRecord();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addMaintenanceRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Equipment ID: ");
        int equipmentId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Maintenance Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Scheduled Maintenance Date (YYYY-MM-DD): ");
        Date maintenanceDate = Date.valueOf(scanner.nextLine());

        String sql = "INSERT INTO Equipment (equipment_id, description, maintenance_schedule) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, equipmentId);
            pstmt.setString(2, description);
            pstmt.setDate(3, maintenanceDate);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Maintenance record added successfully.");
            } else {
                System.out.println("Failed to add maintenance record.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void viewMaintenanceSchedules() {
        String sql = "SELECT * FROM Equipment ORDER BY maintenance_schedule";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Equipment ID: " + rs.getInt("equipment_id") +
                        ", Description: " + rs.getString("description") +
                        ", Maintenance Date: " + rs.getDate("maintenance_schedule"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void updateMaintenanceRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Equipment ID for update: ");
        int equipmentId = Integer.parseInt(scanner.nextLine());
        System.out.print("Update Maintenance Description: ");
        String description = scanner.nextLine();
        System.out.print("Update Maintenance Date (YYYY-MM-DD): ");
        Date maintenanceDate = Date.valueOf(scanner.nextLine());

        String sql = "UPDATE Equipment SET description = ?, maintenance_schedule = ? WHERE equipment_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setDate(2, maintenanceDate);
            pstmt.setInt(3, equipmentId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Maintenance record updated successfully.");
            } else {
                System.out.println("Failed to update maintenance record.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    public static void updateClassSchedules() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nUpdate Class Schedules");
        System.out.print("Enter Session ID to update: ");
        int sessionId = Integer.parseInt(scanner.nextLine());

        System.out.print("Update Trainer ID (leave blank if no change): ");
        String trainerId = scanner.nextLine();
        System.out.print("Update Room ID (leave blank if no change): ");
        String roomId = scanner.nextLine();
        System.out.print("Update Session Type (leave blank if no change): ");
        String sessionType = scanner.nextLine();
        System.out.print("Update Session Time (YYYY-MM-DD HH:MM:SS) (leave blank if no change): ");
        String sessionTime = scanner.nextLine();
        System.out.print("Update Status (scheduled, cancelled, completed) (leave blank if no change): ");
        String status = scanner.nextLine();

        try {
            StringBuilder sqlBuilder = new StringBuilder("UPDATE Sessions SET ");
            boolean first = true;

            if (!trainerId.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("trainer_id = ").append(Integer.parseInt(trainerId));
                first = false;
            }
            if (!roomId.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("room_id = ").append(Integer.parseInt(roomId));
                first = false;
            }
            if (!sessionType.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("session_type = '").append(sessionType).append("'");
                first = false;
            }
            if (!sessionTime.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("session_time = '").append(Timestamp.valueOf(sessionTime)).append("'");
                first = false;
            }
            if (!status.isEmpty()) {
                if (!first) sqlBuilder.append(", ");
                sqlBuilder.append("status = '").append(status).append("'");
            }
            sqlBuilder.append(" WHERE session_id = ").append(sessionId);

            try (PreparedStatement pstmt = connection.prepareStatement(sqlBuilder.toString())) {
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Class schedule updated successfully.");
                } else {
                    System.out.println("Failed to update class schedule. Please check the session ID and try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void processBilling() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nBilling Management");
            System.out.println("1. Add Billing Record");
            System.out.println("2. View All Billing Records");
            System.out.println("3. Update Billing Status");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBillingRecord();
                    break;
                case "2":
                    viewAllBillingRecords();
                    break;
                case "3":
                    updateBillingStatus();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addBillingRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Member ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Billing Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Payment Status (pending, completed, failed): ");
        String status = scanner.nextLine();

        String sql = "INSERT INTO Billing (member_id, amount, payment_status) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, status);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Billing record added successfully.");
            } else {
                System.out.println("Failed to add billing record.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void viewAllBillingRecords() {
        String sql = "SELECT * FROM Billing";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Billing ID: " + rs.getInt("billing_id") +
                        ", Member ID: " + rs.getInt("member_id") +
                        ", Amount: $" + rs.getDouble("amount") +
                        ", Status: " + rs.getString("payment_status"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void updateBillingStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Billing ID to update: ");
        int billingId = Integer.parseInt(scanner.nextLine());
        System.out.print("Update Payment Status (pending, completed, failed): ");
        String status = scanner.nextLine();

        String sql = "UPDATE Billing SET payment_status = ? WHERE billing_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, billingId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Billing status updated successfully.");
            } else {
                System.out.println("Failed to update billing status. Please check the billing ID.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

}

