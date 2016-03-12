package net.dkcraft.opticore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.earth2me.essentials.Essentials;

import net.dkcraft.opticore.Main;

public class NotificationListener implements Listener {

	public Main plugin;

	public NotificationListener(Main plugin) {
		this.plugin = plugin;
	}

	Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		String world = player.getWorld().getName();
		if (world.toLowerCase().startsWith("guest")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.WHITE + world);
		} else if (world.toLowerCase().startsWith("member")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.GRAY + world);
		} else if (world.toLowerCase().startsWith("recruit")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.GOLD + world);
		} else if (world.toLowerCase().startsWith("builder")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.GREEN + world);
		} else if (world.toLowerCase().startsWith("crafter")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.DARK_PURPLE + world);
		} else if (world.toLowerCase().startsWith("operator")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.AQUA + world);
		} else if (world.toLowerCase().startsWith("admin")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.BLUE + world);
		} else if (world.toLowerCase().startsWith("owner")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.RED + world);
		} else if (world.toLowerCase().startsWith("lobby")) {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + ChatColor.BLUE + world);
		} else {
			event.setJoinMessage(ChatColor.YELLOW + playerName + " has connected. Joined map " + world);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		event.setQuitMessage(ChatColor.YELLOW + playerName + " has left the server.");
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		for (Player notification : Bukkit.getOnlinePlayers()) {
			Player player = event.getPlayer();
			String playerName = player.getName();
			String world = player.getWorld().getName();
			if (!plugin.toggleNotifications.contains(notification.getName())) {
				if (!plugin.deafen.contains(notification.getName())) {
					if (essentials != null) {
						if (!essentials.getUser(player).isVanished()) {
							if (world.toLowerCase().startsWith("guest")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.WHITE + world);
							} else if (world.toLowerCase().contains("member")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.GRAY + world);
							} else if (world.toLowerCase().contains("recruit")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.GOLD + world);
							} else if (world.toLowerCase().contains("builder")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.GREEN + world);
							} else if (world.toLowerCase().contains("crafter")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.DARK_PURPLE + world);
							} else if (world.toLowerCase().contains("operator")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.AQUA + world);
							} else if (world.toLowerCase().contains("admin")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.BLUE + world);
							} else if (world.toLowerCase().contains("owner")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.RED + world);
							} else if (world.toLowerCase().contains("lobby")) {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + ChatColor.BLUE + world);
							} else {
								notification.sendMessage(ChatColor.YELLOW + playerName + " changed map to " + world);
							}
						}
					}
				}
			}
		}
	}
}
