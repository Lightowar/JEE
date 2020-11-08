package gestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreationTable {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		try (Connection connect = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/banque?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC", "BANQUE",
				"BANQUE");) {

			Statement statement = connect.createStatement();

			try {
				statement.execute("CREATE TABLE COMPTE ("
						+ "NOCOMPTE CHAR(4) PRIMARY KEY NOT NULL,"
						+ "NOM VARCHAR(20),"
						+ "PRENOM VARCHAR(20),"
						+ "SOLDE DECIMAL(10, 2) NOT NULL"
						+ ")");
			} catch (SQLException e) {
				System.err.println("Error in creation of the table COMPTE : " + e.getMessage());
			}

			try {
				statement.execute("CREATE TABLE OPERATION ("
						+ "NOCOMPTE CHAR(4) NOT NULL,"
						+ "DATE DATE NOT NULL,"
						+ "HEURE TIME,"
						+ "OP CHAR(1) NOT NULL,"
						+ "VALEUR DECIMAL(10, 2) NOT NULL"
						+ ")");
			} catch (SQLException e) {
				System.err.println("Error in creation of the table COMPTE : " + e.getMessage());
			}
		}
	}
}
