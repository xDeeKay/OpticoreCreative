package net.dkcraft.opticore.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.scheduler.BukkitRunnable;

public class BlockPlaceHandler extends BukkitRunnable {

	private final Main plugin;
	private MySQL mysql;
	
	public BlockPlaceHandler(Main plugin, String playerName, String uuid) {
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
			if (plugin.statsBlockPlace.containsKey(playerName) && plugin.statsBlockPlace.get(playerName) != 0) {
				try {
					int previousBlocksPlaced = 0;

					PreparedStatement sql = mysql.connection
							.prepareStatement("SELECT blocks_placed FROM `player_stats` WHERE uuid=?;");
					sql.setString(1, uuid);

					ResultSet result = sql.executeQuery();
					result.next();

					previousBlocksPlaced = result.getInt("blocks_placed");

					PreparedStatement placedUpdate = mysql.connection
							.prepareStatement("UPDATE `player_stats` SET blocks_placed=? WHERE uuid=?;");
					placedUpdate.setInt(1, previousBlocksPlaced + plugin.statsBlockPlace.get(playerName));
					placedUpdate.setString(2, uuid);
					placedUpdate.executeUpdate();

					placedUpdate.close();
					sql.close();
					result.close();

					plugin.statsBlockPlace.remove(playerName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				plugin.statsBlockPlace.remove(playerName);
			}
		}
	}
}