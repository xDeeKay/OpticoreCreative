package net.dkcraft.opticore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;

import net.dkcraft.opticore.Main;

public class MySQL {

	public Main plugin;
	
	public Connection connection;

	public MySQL(Main plugin) {
		this.plugin = plugin;
	}

	public synchronized void openConnection() {
		try {
			String host = plugin.getConfig().getString("mysql.host");
			String port = plugin.getConfig().getString("mysql.port");
			String database = plugin.getConfig().getString("mysql.database");
			String user = plugin.getConfig().getString("mysql.user");
			String password = plugin.getConfig().getString("mysql.password");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
			Bukkit.getConsoleSender().sendMessage("[Opticore] MySQL connection opened successfully.");
		} catch(Exception e) {
			Bukkit.getConsoleSender().sendMessage("[Opticore] MySQL connection failed.");
			e.printStackTrace();
		}
	}

	public synchronized void closeConnection() {
		try {
			connection.close();
			Bukkit.getConsoleSender().sendMessage("[Opticore] MySQL connection closed successfully.");
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("[Opticore] MySQL disconnection failed.");
			e.printStackTrace();
		}
	}

	public synchronized boolean playerDataContainsUUID(String uuid) {
		try {
			PreparedStatement sql = connection
					.prepareStatement("SELECT * FROM `player_stats` WHERE uuid=?;");
			sql.setString(1, uuid);
			ResultSet resultSet = sql.executeQuery();
			boolean containsUUID = resultSet.next();

			sql.close();
			resultSet.close();

			return containsUUID;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
