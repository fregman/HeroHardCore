package de.herobrine.herohardcore.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.herobrine.herohardcore.HeroHardCore;

public class MySQL {

	static HeroHardCore plugin = null;

	private static Connection con = null;
	
	 private String tableName;

	public MySQL(HeroHardCore plugin) {
		
		this.tableName = plugin.getConfig().getString("mysql.tablename");

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
			System.out.println("Driver not found");
		} catch (SQLException e) {
			System.out.println("Couldn't connect to MySQL Server");
			e.printStackTrace();
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

			try {
	
				String querySql = "SHOW TABLES LIKE ?";

				PreparedStatement preparedQueryStatement = con
						.prepareStatement(querySql);
				preparedQueryStatement.setString(1, tableName);
				ResultSet result = preparedQueryStatement.executeQuery();

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

				String sql = "CREATE TABLE " + tableName + "(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), name VARCHAR(30),  " +
						     "loginTime TIMESTAMP, playTime INT, isdeath CHAR)";

				query.execute(sql);

				
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

	}

	public boolean checkPlayer(String name) {

		boolean success = false;

		con = getInstance();

		if (con != null) {

			try {

				String querySql = "SELECT *" + "FROM " + tableName
						+ " WHERE name = ?";

				PreparedStatement preparedQueryStatement = con
						.prepareStatement(querySql);
				preparedQueryStatement.setString(1, name);
				ResultSet result = preparedQueryStatement.executeQuery();

				success = result.next();

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		return success;
	}

	public void insertTime(String playerName) {


		con = getInstance();

		if (con != null) {

			try {
				String updateSql = "UPDATE " + tableName + " SET loginTime = NOW()"
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


		con = getInstance();

		if (con != null) {
			try {

				String sql = "INSERT INTO " + tableName + "(name, loginTime, playTime, isdeath) "
						+ "VALUES(?, NOW(), ?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);

				preparedStatement.setString(1, playerName);

				preparedStatement.setInt(2, 0);
				
				preparedStatement.setString(3, "F");

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void updatePlayer(String playerName) {



		con = getInstance();

		if (con != null) {
			try {

				String diffSql = "SELECT playTime, TIMESTAMPDIFF(second, loginTime, NOW()) AS diff "
						+ "FROM " + tableName + " WHERE name = ?";

				PreparedStatement diffStatement = con.prepareStatement(diffSql);
				diffStatement.setString(1, playerName);

				String updateSql = "UPDATE " + tableName
						+ " SET playTime = ? , loginTime = NOW() "
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

				String diffSql = "SELECT playTime " + "FROM " + tableName 
						+ " WHERE name = ?";

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
				String updateSql = "UPDATE " + tableName + " SET playTime = 0"
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

	public void setDeathtrue(String playerName) {
		
		con = getInstance();


		if (con != null) {

			try {
				String updateSql = "UPDATE " + tableName
						+ " SET playTime = ? , isDeath = ? "
						+ "WHERE name = ?";
				PreparedStatement preparedUpdateStatement;

				preparedUpdateStatement = con.prepareStatement(updateSql);
				preparedUpdateStatement.setString(2, "T");
				preparedUpdateStatement.setInt(1, getPlayTime(playerName));
				preparedUpdateStatement.setString(3, playerName);

				preparedUpdateStatement.executeUpdate();

			

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		
	}

	public ArrayList<String> getTopList() {
		
		con = getInstance();
		ArrayList<String> toplist = new ArrayList<String>();

		if (con != null) {
			try {

				String diffSql = "SELECT name, playTime " + "FROM " + tableName 
						+ " ORDER BY playTime DESC";

				PreparedStatement diffStatement = con.prepareStatement(diffSql);


				ResultSet result = diffStatement.executeQuery();
				
				

				while(result.next()){
					
					
					
					String test = String.format("%1$-20s %2$-15s", result.getString("name"), convertTime(result.getInt("playTime")));
					
					toplist.add(test);
					
				
				}



			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return toplist;

		
	}

	private String convertTime(int playTime) {
		
		int sec = playTime % 60;
		int min = playTime / 60 % 60;
		int hour = playTime / 60 / 60 % 24;
		int day = playTime / 60 / 60 / 24;

		String playTimeString = day + "d / " + hour + "h / " + min + "m / " + sec + "s ";
		
		return playTimeString;
	}

}
