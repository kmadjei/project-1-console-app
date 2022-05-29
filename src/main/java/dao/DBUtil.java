package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	static Connection conn = null;;

	static {
		try {
			// step 1
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// singleton design pattern
	// only one connection object throughout he application

	static Connection makeConnection() throws SQLException {

		// step 2 - establish connection to database server
		// connectionURL has the protocol, ipaddress, port number, and database

		String connectionUrl = "jdbc:postgresql://localhost:5432/pp1";
		String userName = "postgres";
		String password = "root";

		if (conn == null) {
			conn = DriverManager.getConnection(connectionUrl, userName, password);
		}

		return conn;
	}

	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}