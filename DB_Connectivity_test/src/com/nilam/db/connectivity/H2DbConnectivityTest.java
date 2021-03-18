package com.nilam.db.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class H2DbConnectivityTest {
	public static void main(String[] args) throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		// add application code here
		// Thread.sleep(1000000000);
		ResultSet rs = conn.createStatement().executeQuery("select * from employee");

		while (rs.next()) {
			System.out.println(" id : " + rs.getInt(1) + " name : " + rs.getString(2));

		}

		conn.close();
	}
}
