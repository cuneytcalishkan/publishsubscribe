package client;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static Connection connection;
	
	public ConnectDB() {
		newConnection();
	}
	
	public static void newConnection(){
		connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username = "okuyucu";
		String password = "oku123yucu";
		String url = "jdbc:mysql://mysql02.natro.com:3306/orenux";
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("connection problem");
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		return connection;
	}

}
