package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class ConnectDB {

    private static Connection connection;

    public static Connection getConnection(String username, String password) throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String url = "jdbc:mysql://mysql02.natro.com:3306/orenux";
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                SLogger.getLogger().log(Level.SEVERE, e.getMessage());
                System.out.println(e);
            }
        }
        return connection;
    }
}
