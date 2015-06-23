package net.dkcraft.opticore.stats.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.scheduler.BukkitRunnable;

public class BlockBreakHandler extends BukkitRunnable {

	private final Main plugin;
	private MySQL mysql;
	
	public BlockBreakHandler(Main plugin, String playerName, String uuid) {
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
			if (plugin.statsBlockBreak.containsKey(playerName) && plugin.statsBlockBreak.get(playerName) != 0) {
				try {
					int previousBlocksBroken = 0;

					PreparedStatement sql = mysql.connection
							.prepareStatement("SELECT blocks_broken FROM `player_stats` WHERE uuid=?;");
					sql.setString(1, uuid);

					ResultSet result = sql.executeQuery();
					result.next();

					previousBlocksBroken = result.getInt("blocks_broken");

					PreparedStatement brokenUpdate = mysql.connection
							.prepareStatement("UPDATE `player_stats` SET blocks_broken=? WHERE uuid=?;");
					brokenUpdate.setInt(1, previousBlocksBroken + plugin.statsBlockBreak.get(playerName));
					brokenUpdate.setString(2, uuid);
					brokenUpdate.executeUpdate();

					brokenUpdate.close();
					sql.close();
					result.close();

					plugin.statsBlockBreak.remove(playerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				plugin.statsBlockBreak.remove(playerName);
			}
		}
	}
}