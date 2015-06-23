package net.dkcraft.opticore.stats.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.scheduler.BukkitRunnable;

public class TimeOnlineHandler extends BukkitRunnable {

	private final Main plugin;
	private MySQL mysql;
	
	public TimeOnlineHandler(Main plugin, String playerName, String uuid) {
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
			if (plugin.statsTimeOnline.containsKey(playerName) && plugin.statsTimeOnline.get(playerName) != 0) {
				try {
					int previousTimeOnline = 0;

					if (mysql.playerDataContainsUUID(uuid)) {
						PreparedStatement sql = mysql.connection
								.prepareStatement("SELECT time_online FROM `player_stats` WHERE uuid=?;");
						sql.setString(1, uuid);

						ResultSet result = sql.executeQuery();
						result.next();

						previousTimeOnline = result.getInt("time_online");
						
						final long diffInSec = (System.currentTimeMillis() - plugin.statsTimeOnline.get(playerName)) / 1000;

						PreparedStatement timeUpdate = mysql.connection
								.prepareStatement("UPDATE `player_stats` SET time_online=? WHERE uuid=?;");
						timeUpdate.setLong(1, previousTimeOnline + diffInSec);
						timeUpdate.setString(2, uuid);
						timeUpdate.executeUpdate();

						timeUpdate.close();
						sql.close();
						result.close();

						plugin.statsTimeOnline.remove(playerName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				plugin.statsTimeOnline.remove(playerName);
			}
		}
	}
}