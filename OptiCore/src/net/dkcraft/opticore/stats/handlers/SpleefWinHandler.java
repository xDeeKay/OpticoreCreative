package net.dkcraft.opticore.stats.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.scheduler.BukkitRunnable;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

public class SpleefWinHandler extends BukkitRunnable {

	private final Main plugin;
	private MySQL mysql;
	
	public SpleefWinHandler(Main plugin, String playerName, String uuid) {
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
			if (plugin.spleefWins.containsKey(playerName) && plugin.spleefWins.get(playerName) != 0) {
				try {
					int previousSpleefWins = 0;

					PreparedStatement sql = mysql.connection
							.prepareStatement("SELECT spleef_wins FROM `player_stats` WHERE uuid=?;");
					sql.setString(1, uuid);

					ResultSet result = sql.executeQuery();
					result.next();

					previousSpleefWins = result.getInt("spleef_wins");

					PreparedStatement placedUpdate = mysql.connection
							.prepareStatement("UPDATE `player_stats` SET spleef_wins=? WHERE uuid=?;");
					placedUpdate.setInt(1, previousSpleefWins + plugin.spleefWins.get(playerName));
					placedUpdate.setString(2, uuid);
					placedUpdate.executeUpdate();

					placedUpdate.close();
					sql.close();
					result.close();

					plugin.spleefWins.remove(playerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				plugin.spleefWins.remove(playerName);
			}
		}
	}
}
