package com.TestNGtests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataBaseClass {

	Connection conn = null;
	Statement stm;

	public Connection dataBaseConnection(String dbname, String username, String password) {

		try {

			// Class.forName( "org.Postgresql.Driver");
			// data base connection
			conn = DriverManager.getConnection(dbname, username, password);

			if (conn != null) {

				System.out.println("connection is established!");
			} else {
				System.out.println("connection is not established!");
			}

		} catch (Exception e) {

		}
		return conn;
	}

	public void createTable() throws SQLException {

		stm = conn.createStatement();

		String Query = "CREATE TABLE IF NOT EXISTS Recipes(\r\n" + "Recipe_id serial,\r\n"
				+ "Recipe_category varchar(100),\r\n" + "Food_category Varchar(100),\r\n" + "Ingredients text\r\n"
				+ ")";

		 stm.executeQuery(Query);
	}

	public void insertTable() throws SQLException {

		stm = conn.createStatement();

		String insertQuery = "INSERT INTO recipe (recipe_category, food_category, ingredients) " + "VALUES "
				+ "('Indian', 'veg', 'rice,dal,salt,chilli'), " +
				"('Chinese', 'non-veg', 'noodles, sauce, chicken')";
		stm.executeUpdate(insertQuery);
		System.out.println("Data inserted into table");
	}

	public void getDatafromTable() throws SQLException {

		stm = conn.createStatement();
		String getQuery = "select * from recipe";

		ResultSet rs2 = stm.executeQuery(getQuery);

		while (rs2.next()) {

			String recipeID = rs2.getString("recipe_id");

			System.out.println(recipeID);

			String recipeCategory = rs2.getString("recipe_category");
			System.out.println(recipeCategory);

		}
	}

}
