package net.dkcraft.opticore.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Methods {

	public Main plugin;

	public Methods(Main plugin) {
		this.plugin = plugin;
	}

	// Check for int
	public boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public String getRank(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> ranks = user.getParentIdentifiers();
		String rank = ranks.get(0).toLowerCase();
		return rank;
	}
	
	@SuppressWarnings("deprecation")
	public void playAlertSound(final Player player, final Location location) {
		Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {
			public void run() {
				player.playNote(player.getLocation(), (byte) 0, (byte) 18);
			}
		});
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				player.playNote(player.getLocation(), (byte) 0, (byte) 20);
			}
		}, 3L);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				player.playNote(player.getLocation(), (byte) 0, (byte) 21);
			}
		}, 6L);
	}
}
