package net.dkcraft.opticore.stats.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.scheduler.BukkitRunnable;

public class ChatHandler extends BukkitRunnable {

	private final Main plugin;
	private MySQL mysql;
	
	public ChatHandler(Main plugin, String playerName, String uuid) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
		this.playerName = playerName;
		this.uuid = uuid;
	}
	
	private String playerName;
	private String uuid;

	@Override
	public void run() {
		if (mysql.playerDataContainsUUID(uuid)) {
			if (plugin.statsChat.containsKey(playerName) && plugin.statsChat.get(playerName) != 0) {
				try {
					int previousLinesSpoken = 0;

					PreparedStatement sql = mysql.connection
							.prepareStatement("SELECT lines_spoken FROM `player_stats` WHERE uuid=?;");
					sql.setString(1, uuid);

					ResultSet result = sql.executeQuery();
					result.next();

					previousLinesSpoken = result.getInt("lines_spoken");

					PreparedStatement spokenUpdate = mysql.connection
							.prepareStatement("UPDATE `player_stats` SET lines_spoken=? WHERE uuid=?;");
					spokenUpdate.setInt(1, previousLinesSpoken + plugin.statsChat.get(playerName));
					spokenUpdate.setString(2, uuid);
					spokenUpdate.executeUpdate();

					spokenUpdate.close();
					sql.close();
					result.close();

					plugin.statsChat.remove(playerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				plugin.statsChat.remove(playerName);
			}
		}
	}
}