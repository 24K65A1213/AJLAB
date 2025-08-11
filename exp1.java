import java.sql.*;

public class exp1 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student"; // Ensure 'college' DB exists
        String user = "root";
        String password = "";

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            // a. Create Table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                                    "roll_no INT PRIMARY KEY," +
                                    "name VARCHAR(50)," +
                                    "address VARCHAR(100))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table created or already exists.");

            // Insert few initial records
            stmt.executeUpdate("INSERT INTO students VALUES (1, 'balaji', 'Hyderabad'), (2, 'Anu', 'Chennai')");
            System.out.println("Initial records inserted.");

            // b. Display Records
            System.out.println("\nRecords after initial insert:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                System.out.println(rs.getInt("roll_no") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("address"));
            }

            // c. Insert Two More Records
            stmt.executeUpdate("INSERT INTO students VALUES (3, 'chaitanya', 'Bangalore')");
            stmt.executeUpdate("INSERT INTO students VALUES (4, 'pavan', 'Mumbai')");
            System.out.println("\nTwo more records inserted.");

            // d. Update One Record
            stmt.executeUpdate("UPDATE students SET address='Pune' WHERE roll_no=2");
            System.out.println("Record with roll_no=2 updated.");

            // e. Delete One Record
            stmt.executeUpdate("DELETE FROM students WHERE roll_no=1");
            System.out.println("Record with roll_no=1 deleted.");

            // f. Display Records Again
            System.out.println("\nFinal Records in the Table:");
            rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                System.out.println(rs.getInt("roll_no") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("address"));
            }

            // Close resources
            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
