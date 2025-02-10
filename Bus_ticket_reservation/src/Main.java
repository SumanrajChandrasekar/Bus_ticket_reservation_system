import java.sql.*;
import java.util.Scanner;

class BusTicketReservation {
    private static final String URL = "jdbc:mysql://localhost:3306/bus_reservation";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Welcome to Bus Ticket Reservation System");
            while (true) {
                System.out.println("1. View Buses\n2. Book Ticket\n3. View Bookings\n4. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: viewBuses(conn); break;
                    case 2: bookTicket(conn, scanner); break;
                    case 3: viewBookings(conn); break;
                    case 4: System.exit(0);
                    default: System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewBuses(Connection conn) throws SQLException {
        String query = "SELECT * FROM buses";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Bus ID | Bus Name | Source | Destination | Seats");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %d\n", rs.getInt("bus_id"), rs.getString("bus_name"), rs.getString("source"), rs.getString("destination"), rs.getInt("seats"));
            }
        }
    }

    private static void bookTicket(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Bus ID: ");
        int busId = scanner.nextInt();
        System.out.print("Enter Your Name: ");
        String name = scanner.next();

        String query = "INSERT INTO bookings (bus_id, passenger_name) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, busId);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Ticket Booked Successfully!");
        }
    }

    private static void viewBookings(Connection conn) throws SQLException {
        String query = "SELECT b.booking_id, b.passenger_name, bu.bus_name FROM bookings b JOIN buses bu ON b.bus_id = bu.bus_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Booking ID | Passenger Name | Bus Name");
            while (rs.next()) {
                System.out.printf("%d | %s | %s\n", rs.getInt("booking_id"), rs.getString("passenger_name"), rs.getString("bus_name"));
            }
        }
    }
}