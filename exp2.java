import java.sql.*;

public class exp2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student"; // Ensure 'student' DB exists
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            // a. Create Table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                                    "roll_no INT PRIMARY KEY," +
                                    "name VARCHAR(50)," +
                                    "address VARCHAR(100))";
            PreparedStatement psCreate = con.prepareStatement(createTableSQL);
            psCreate.executeUpdate();
            psCreate.close();
            System.out.println("Table created or already exists.");

            // Insert few initial records
            String insertSQL = "INSERT INTO students (roll_no, name, address) VALUES (?, ?, ?)";
            PreparedStatement psInsert = con.prepareStatement(insertSQL);
            psInsert.setInt(1, 1);
            psInsert.setString(2, "Ravi");
            psInsert.setString(3, "Hyderabad");
            psInsert.executeUpdate();

            psInsert.setInt(1, 2);
            psInsert.setString(2, "Anu");
            psInsert.setString(3, "Chennai");
            psInsert.executeUpdate();
            psInsert.close();
            System.out.println("Initial records inserted.");

            // b. Display Records
            System.out.println("\nRecords after initial insert:");
            displayRecords(con);

            // c. Insert Two More Records
            psInsert = con.prepareStatement(insertSQL);
            psInsert.setInt(1, 3);
            psInsert.setString(2, "Kiran");
            psInsert.setString(3, "Bangalore");
            psInsert.executeUpdate();

            psInsert.setInt(1, 4);
            psInsert.setString(2, "Sneha");
            psInsert.setString(3, "Mumbai");
            psInsert.executeUpdate();
            psInsert.close();
            System.out.println("\nTwo more records inserted.");

            // d. Update One Record
            String updateSQL = "UPDATE students SET address=? WHERE roll_no=?";
            PreparedStatement psUpdate = con.prepareStatement(updateSQL);
            psUpdate.setString(1, "Pune");
            psUpdate.setInt(2, 2);
            psUpdate.executeUpdate();
            psUpdate.close();
            System.out.println("Record with roll_no=2 updated.");

            // e. Delete One Record
            String deleteSQL = "DELETE FROM students WHERE roll_no=?";
            PreparedStatement psDelete = con.prepareStatement(deleteSQL);
            psDelete.setInt(1, 1);
            psDelete.executeUpdate();
            psDelete.close();
            System.out.println("Record with roll_no=1 deleted.");

            // f. Display Records Again
            System.out.println("\nFinal Records in the Table:");
            displayRecords(con);

            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to display records from students table using PreparedStatement
    public static void displayRecords(Connection con) throws SQLException {
        String selectSQL = "SELECT * FROM students";
        PreparedStatement psSelect = con.prepareStatement(selectSQL);
        ResultSet rs = psSelect.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt("roll_no") + " | " +
                               rs.getString("name") + " | " +
                               rs.getString("address"));
        }
        rs.close();
    }
}
