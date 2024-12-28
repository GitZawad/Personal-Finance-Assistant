import java.sql.*;
import java.util.*;

public class Main {
    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize MySQL connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_db", "root", "1234");
            System.out.println("Connected to MySQL Database.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return;
        }

        // Admin account pre-setup
        // Add admin to the database if not already present
        try {
            String query = "INSERT IGNORE INTO users (user_id, password, role) VALUES ('001', '001', 'admin')";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Main menu
        while (true) {
            System.out.println("Welcome to the finance assistant. Please select your role.");
            System.out.println("1. Administrator");
            System.out.println("2. User");
            String roleChoice = scanner.nextLine();

            if (roleChoice.equals("1")) {
                if (login("admin")) {
                    adminMenu();
                }
            } else if (roleChoice.equals("2")) {
                if (login("user")) {
                    userMenu();
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    // Login function
    private static boolean login(String role) {
        System.out.println("Please enter your account number:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        try {
            String query = "SELECT * FROM users WHERE user_id = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (role.equals("admin") && rs.getString("role").equals("admin")) {
                    System.out.println("Welcome Admin.");
                    return true;
                } else if (role.equals("user") && rs.getString("role").equals("user")) {
                    System.out.println("Welcome User.");
                    return true;
                } else {
                    System.out.println("Incorrect role.");
                    return false;
                }
            } else {
                System.out.println("Username or password is incorrect.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Admin menu
    private static void adminMenu() {
        while (true) {
            System.out.println("Please select the operation you wish to perform. To log out, please enter EXIT.");
            System.out.println("1. User management");
            System.out.println("2. Income category management");
            System.out.println("3. Expenditure category management");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                userManagement();
            } else if (choice.equals("2")) {
                incomeCategoryManagement();
            } else if (choice.equals("3")) {
                expenditureCategoryManagement();
            } else if (choice.equalsIgnoreCase("EXIT")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // User management
    private static void userManagement() {
        System.out.println("1. Create user");
        System.out.println("2. Delete user");
        System.out.println("3. View all users");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            createUser();
        } else if (choice.equals("2")) {
            deleteUser();
        } else if (choice.equals("3")) {
            viewAllUsers();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Create new user
    private static void createUser() {
        System.out.println("Enter new user ID:");
        String userId = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try {
            String query = "INSERT INTO users (user_id, password, role) VALUES (?, ?, 'user')";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, password);
            ps.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete user
    private static void deleteUser() {
        System.out.println("Enter user ID to delete:");
        String userId = scanner.nextLine();

        try {
            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, userId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all users
    private static void viewAllUsers() {
        try {
            String query = "SELECT user_id FROM users";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("User ID: " + rs.getString("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Income category management
    private static void incomeCategoryManagement() {
        System.out.println("1. Create income category");
        System.out.println("2. View income categories");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            createCategory("income");
        } else if (choice.equals("2")) {
            viewCategories("income");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Expenditure category management
    private static void expenditureCategoryManagement() {
        System.out.println("1. Create expenditure category");
        System.out.println("2. View expenditure categories");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            createCategory("expenditure");
        } else if (choice.equals("2")) {
            viewCategories("expenditure");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Create category
    private static void createCategory(String type) {
        System.out.println("Enter category name:");
        String categoryName = scanner.nextLine();

        try {
            String query = "INSERT INTO categories (name, type) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, categoryName);
            ps.setString(2, type);
            ps.executeUpdate();
            System.out.println("Category added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View categories
    private static void viewCategories(String type) {
        try {
            String query = "SELECT name FROM categories WHERE type = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Category: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // User menu
    private static void userMenu() {
        while (true) {
            System.out.println("1. Add income record");
            System.out.println("2. Add expenditure record");
            System.out.println("3. View records");
            System.out.println("4. Calculate total income, expenditure, and balance");
            System.out.println("5. Exit");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                addIncomeRecord();
            } else if (choice.equals("2")) {
                addExpenditureRecord();
            } else if (choice.equals("3")) {
                viewRecords();
            } else if (choice.equals("4")) {
                calculateBalance();
            } else if (choice.equals("5")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // Add income record
    private static void addIncomeRecord() {
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter date (yyyy-mm-dd):");
        String date = scanner.nextLine();

        try {
            String query = "SELECT category_id FROM categories WHERE name = ? AND type = 'income'";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int categoryId = rs.getInt("category_id");

                String insertQuery = "INSERT INTO records (user_id, category_id, amount, date) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                insertPs.setString(1, "user1"); // Assuming user1 for now
                insertPs.setInt(2, categoryId);
                insertPs.setDouble(3, amount);
                insertPs.setString(4, date);
                insertPs.executeUpdate();
                System.out.println("Income record added.");
            } else {
                System.out.println("Invalid category.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add expenditure record
    private static void addExpenditureRecord() {
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter date (yyyy-mm-dd):");
        String date = scanner.nextLine();

        try {
            String query = "SELECT category_id FROM categories WHERE name = ? AND type = 'expenditure'";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int categoryId = rs.getInt("category_id");

                String insertQuery = "INSERT INTO records (user_id, category_id, amount, date) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPs = connection.prepareStatement(insertQuery);
                insertPs.setString(1, "user1"); // Assuming user1 for now
                insertPs.setInt(2, categoryId);
                insertPs.setDouble(3, amount);
                insertPs.setString(4, date);
                insertPs.executeUpdate();
                System.out.println("Expenditure record added.");
            } else {
                System.out.println("Invalid category.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View records
    private static void viewRecords() {
        try {
            String query = "SELECT r.amount, r.date, c.name AS category FROM records r " +
                    "JOIN categories c ON r.category_id = c.category_id " +
                    "WHERE r.user_id = 'user1'"; // Assuming user1 for now

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("Category: " + rs.getString("category") +
                        ", Amount: " + rs.getDouble("amount") +
                        ", Date: " + rs.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Calculate total income, expenditure, and balance
    private static void calculateBalance() {
        double totalIncome = 0, totalExpenditure = 0;

        try {
            // Calculate total income
            String incomeQuery = "SELECT SUM(amount) AS total FROM records r " +
                    "JOIN categories c ON r.category_id = c.category_id " +
                    "WHERE r.user_id = 'user1' AND c.type = 'income'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(incomeQuery);
            if (rs.next()) {
                totalIncome = rs.getDouble("total");
            }

            // Calculate total expenditure
            String expenditureQuery = "SELECT SUM(amount) AS total FROM records r " +
                    "JOIN categories c ON r.category_id = c.category_id " +
                    "WHERE r.user_id = 'user1' AND c.type = 'expenditure'";
            rs = stmt.executeQuery(expenditureQuery);
            if (rs.next()) {
                totalExpenditure = rs.getDouble("total");
            }

            double balance = totalIncome - totalExpenditure;
            System.out.println("Total Income: " + totalIncome);
            System.out.println("Total Expenditure: " + totalExpenditure);
            System.out.println("Balance: " + balance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
