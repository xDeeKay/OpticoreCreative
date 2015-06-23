package net.dkcraft.opticore.stats.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.scheduler.BukkitRunnable;

public class SpleefLossHandler extends BukkitRunnable {

	private final Main plugin;
	private MySQL mysql;
	
	public SpleefLossHandler(Main plugin, String playerName, String uuid) {
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
			if (plugin.spleefLosses.containsKey(playerName) && plugin.spleefLosses.get(playerName) != 0) {
				try {
					int previousSpleefLosses = 0;

					PreparedStatement sql = mysql.connection
							.prepareStatement("SELECT spleef_losses FROM `player_stats` WHERE uuid=?;");
					sql.setString(1, uuid);

					ResultSet result = sql.executeQuery();
					result.next();

					previousSpleefLosses = result.getInt("spleef_losses");

					PreparedStatement placedUpdate = mysql.connection
							.prepareStatement("UPDATE `player_stats` SET spleef_losses=? WHERE uuid=?;");
					placedUpdate.setInt(1, previousSpleefLosses + plugin.spleefLosses.get(playerName));
					placedUpdate.setString(2, uuid);
					placedUpdate.executeUpdate();

					placedUpdate.close();
					sql.close();
					result.close();

					plugin.spleefLosses.remove(playerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				plugin.spleefLosses.remove(playerName);
			}
		}
	}
}