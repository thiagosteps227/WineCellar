package com.ait.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.ait.wine.Wine;

public class UtilsDAO {

	public void resetTable(List<Wine> wines) throws Exception {
		String driver = null;
		Connection connection = null;
		Statement statement = null;
		PreparedStatement ps = null;
		String url;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://localhost/winedb?user=root";
			// driver="com.mysql.jdbc.Driver";
			driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			connection = DriverManager.getConnection(url);
			String query = "TRUNCATE TABLE wine";
			statement = connection.createStatement();
			statement.execute(query);
			for (Wine wine : wines) {

				ps = connection.prepareStatement("INSERT INTO wine (name, grapes, country, year) VALUES (?, ?, ?, ?)",
						new String[] { "ID" });
				ps.setString(1, wine.getName());
				ps.setString(2, wine.getGrapes());
				ps.setString(3, wine.getCountry());
				ps.setString(4, wine.getYear());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		}

	}

}

