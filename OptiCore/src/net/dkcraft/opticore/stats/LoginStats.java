package net.dkcraft.opticore.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginStats implements Listener {

	public Main plugin;
	private MySQL mysql;
	
	public LoginStats(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		final String playerName = event.getPlayer().getName();
		final String uuid = event.getPlayer().getUniqueId().toString();

		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
			public void run() {
				try {
					int previousTotalLogins = 0;

					if (mysql.playerDataContainsUUID(uuid)) {
						PreparedStatement sql = mysql.connection
								.prepareStatement("SELECT total_logins FROM `player_stats` WHERE uuid=?;");
						sql.setString(1, uuid);

						ResultSet result = sql.executeQuery();
						result.next();

						previousTotalLogins = result.getInt("total_logins");

						PreparedStatement loginsUpdate = mysql.connection
								.prepareStatement("UPDATE `player_stats` SET total_logins=? WHERE uuid=?;");
						loginsUpdate.setInt(1, previousTotalLogins + 1);
						loginsUpdate.setString(2, uuid);
						loginsUpdate.executeUpdate();

						PreparedStatement nameUpdate = mysql.connection
								.prepareStatement("UPDATE `player_stats` SET current_name=? WHERE uuid=?;");
						nameUpdate.setString(1, playerName);
						nameUpdate.setString(2, uuid);
						nameUpdate.executeUpdate();

						PreparedStatement onlineUpdate = mysql.connection
								.prepareStatement("UPDATE `player_stats` SET last_online=? WHERE uuid=?;");
						onlineUpdate.setString(1, "now");
						onlineUpdate.setString(2, uuid);
						onlineUpdate.executeUpdate();

						loginsUpdate.close();
						onlineUpdate.close();
						sql.close();
						result.close();
					} else {
						PreparedStatement newPlayer = mysql.connection
								.prepareStatement("INSERT INTO `player_stats` values(?,?,?,?,1,0,0,0,0,0,0,0);");
						newPlayer.setString(1, uuid);
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
						newPlayer.setString(2, playerName);
						newPlayer.setString(3, format.format(System.currentTimeMillis()));
						newPlayer.setString(4, "now");
						newPlayer.execute();
						newPlayer.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}