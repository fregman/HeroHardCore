package de.herobrine.herohardcore.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.herobrine.herohardcore.HeroHardCore;

public class MySQL {

	static final HeroHardCore plugin = null;

	private static Connection con = null;

	public MySQL(HeroHardCore plugin) {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://"
					+ plugin.getConfig().getString("mysql.host") + ":"
					+ plugin.getConfig().getString("mysql.port") + "/"
					+ plugin.getConfig().getString("mysql.database") + "?"
					+ "user=" + plugin.getConfig().getString("mysql.username")
					+ "&" + "password="
					+ plugin.getConfig().getString("mysql.password"));

		} catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");
		} catch (SQLException e) {
			System.out.println("Connect nicht moeglich");
		}

	}

	private static Connection getInstance() {
		if (con == null)
			new MySQL(plugin);
		return con;
	}

	public boolean checkTable() {

		boolean success = false;

		con = getInstance();

		if (con != null) {

			Statement query;

			try {
				query = con.createStatement();

				String sql = "SHOW TABLES LIKE 'player'";

				ResultSet result = query.executeQuery(sql);

				success = result.next();

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		return success;

	}

	public void createTable() {

		con = getInstance();

		if (con != null) {

			Statement query;

			try {
				query = con.createStatement();

				String sql = "CREATE TABLE player(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), name VARCHAR(30),  loginTime TIMESTAMP, playTime INT)";

				query.execute(sql);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

	}

	public boolean checkPlayer(String name) {

		System.out.println("Player wird geprüft: " + name);

		boolean success = false;

		con = getInstance();

		if (con != null) {

			try {

				String querySql = "SELECT *" + "FROM player "
						+ "WHERE name = ?";

				// PreparedStatement erzeugen.
				PreparedStatement preparedQueryStatement = con
						.prepareStatement(querySql);
				preparedQueryStatement.setString(1, name);
				ResultSet result = preparedQueryStatement.executeQuery();

				success = result.next();

				if (success) {

					System.out.println("player vorhanden");

				} else {

					System.out.println("palyer fehlt");
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		return success;
	}

	public void insertTime(String playerName) {

		System.out.println("Zeit wird eingetragen");

		con = getInstance();

		if (con != null) {

			try {
				String updateSql = "UPDATE player " + "SET loginTime = NOW()"
						+ "WHERE name = ?";
				PreparedStatement preparedUpdateStatement;

				preparedUpdateStatement = con.prepareStatement(updateSql);
				preparedUpdateStatement.setString(1, playerName);

				preparedUpdateStatement.executeUpdate();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

	}

	public void createPlayer(String playerName) {

		System.out.println("spieler wird erstellt");

		con = getInstance();

		if (con != null) {
			try {

				String sql = "INSERT INTO player(name, loginTime, playTime) "
						+ "VALUES(?, NOW(), ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);

				preparedStatement.setString(1, playerName);

				preparedStatement.setInt(2, 0);

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void updatePlayer(String playerName) {

		System.out.println("spieler wird aktualisiert");

		con = getInstance();

		if (con != null) {
			try {

				String diffSql = "SELECT playTime, TIMESTAMPDIFF(second, loginTime, NOW()) AS diff "
						+ "FROM player " + "WHERE name = ?";

				PreparedStatement diffStatement = con.prepareStatement(diffSql);
				diffStatement.setString(1, playerName);

				String updateSql = "UPDATE player "
						+ "SET playTime = ? , loginTime = NOW() "
						+ "WHERE name = ?";
				PreparedStatement preparedUpdateStatement;

				preparedUpdateStatement = con.prepareStatement(updateSql);

				ResultSet result = diffStatement.executeQuery();

				result.next();

				preparedUpdateStatement.setInt(1,
						(result.getInt("diff") + result.getInt("playTime")));
				preparedUpdateStatement.setString(2, playerName);

				preparedUpdateStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int getPlayTime(String playerName) {

		con = getInstance();
		int playTime = 0;

		if (con != null) {
			try {

				String diffSql = "SELECT playTime " + "FROM player "
						+ "WHERE name = ?";

				PreparedStatement diffStatement = con.prepareStatement(diffSql);
				diffStatement.setString(1, playerName);

				ResultSet result = diffStatement.executeQuery();

				result.next();

				playTime = result.getInt("playTime");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return playTime;
	}

	public boolean resetPlayer(String playerName) {

		con = getInstance();
		boolean success = false;

		if (con != null) {

			try {
				String updateSql = "UPDATE player " + "SET playTime = 0"
						+ "WHERE name = ?";
				PreparedStatement preparedUpdateStatement;

				preparedUpdateStatement = con.prepareStatement(updateSql);
				preparedUpdateStatement.setString(1, playerName);

				preparedUpdateStatement.executeUpdate();

				success = true;

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return success;
	}

}
