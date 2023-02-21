import java.sql.*;

public class MySQLConnect {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");

            System.out.println("Connection to MySQL database established!");

            connection.close();
        } catch (Exception e) {
            System.out.println("Error: unable to connect to database");
            e.printStackTrace();
        }
    }
}
