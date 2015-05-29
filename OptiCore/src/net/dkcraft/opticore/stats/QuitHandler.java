package net.dkcraft.opticore.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.scheduler.BukkitRunnable;

public class QuitHandler extends BukkitRunnable {

	private final Main plugin;
	private MySQL mysql;
	
	public QuitHandler(Main plugin, String uuid) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
		this.uuid = uuid;
	}
	
	private String uuid;

	@Override
	public void run() {
		try {
			if (mysql.playerDataContainsUUID(uuid)) {
				PreparedStatement sql = mysql.connection
						.prepareStatement("SELECT last_online FROM `player_stats` WHERE uuid=?;");
				sql.setString(1, uuid);

				ResultSet result = sql.executeQuery();
				result.next();

				PreparedStatement loginsUpdate = mysql.connection
						.prepareStatement("UPDATE `player_stats` SET last_online=? WHERE uuid=?;");
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				loginsUpdate.setString(1, format.format(System.currentTimeMillis()));
				loginsUpdate.setString(2, uuid);
				loginsUpdate.executeUpdate();

				loginsUpdate.close();
				sql.close();
				result.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}