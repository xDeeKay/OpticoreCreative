package net.dkcraft.opticore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.dkcraft.opticore.Main;

public class StaffChat implements Listener {

	public Main plugin;

	public StaffChat(Main plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		String message = event.getMessage();

		if (plugin.staffChat.contains(playerName) && !plugin.msgChannel.containsKey(playerName)) {
			if (!plugin.deafen.contains(playerName)) {
				for (Player staff : Bukkit.getOnlinePlayers()) {
					if (staff.hasPermission("opticore.staffchat")) {
						staff.sendMessage(ChatColor.BLACK + "[S] " + getRank(player) + playerName + ChatColor.WHITE + ": " + message);
						event.setCancelled(true);
					}
				}
			}
		}
	}

	public String getRank(Player player) {
		return player.hasPermission("opticore.staffchat.owner") ? ChatColor.WHITE + "[" + ChatColor.DARK_RED + "Owner" + ChatColor.WHITE + "] " + ChatColor.DARK_RED :
			player.hasPermission("opticore.staffchat.admin") ? ChatColor.WHITE + "[" + ChatColor.BLUE + "Admin" + ChatColor.WHITE + "] " + ChatColor.BLUE :
				player.hasPermission("opticore.staffchat.operator") ? ChatColor.WHITE + "[" + ChatColor.AQUA + "Operator" + ChatColor.WHITE + "] " + ChatColor.AQUA :
					player.hasPermission("opticore.staffchat.developer") ? ChatColor.WHITE + "[" + ChatColor.DARK_GREEN + "Developer" + ChatColor.WHITE + "] " + ChatColor.DARK_GREEN : "{No rank}";
	}
}
