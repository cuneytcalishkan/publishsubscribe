package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class ConnectDB {

    private static Connection connection;

    public static Connection getConnection(String host, String username, String password) throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println(e);
                SLogger.getLogger().log(Level.SEVERE, e.getMessage());
            }
            String url = "jdbc:mysql://" + host;
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                SLogger.getLogger().log(Level.SEVERE, e.getMessage());
            }
        }
        return connection;
    }
}
