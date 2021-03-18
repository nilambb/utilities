package com.nilam.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DbConnectivityTest {

	public static void main(String[] args) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con =DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/demo","root","root"); 
		
		//Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		// add application code here
		// Thread.sleep(1000000000);
		ResultSet rs = con.createStatement().executeQuery("select * from employee");

		while (rs.next()) {
			System.out.println(" id : " + rs.getInt(1) + " name : " + rs.getString(2));

		}

		con.close();
	}
}
