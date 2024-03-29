package net.dkcraft.opticore.listeners;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Methods;

public class ChatListener implements Listener {

	public Main plugin;
	private Methods methods;

	public ChatListener(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		String message = event.getMessage();
		if (plugin.chatRepeat.containsKey(playerName) && message.equalsIgnoreCase(plugin.chatRepeat.get(playerName))) {
			if (!player.hasPermission("opticore.bypass.chatrepeat")) {
				player.sendMessage(ChatColor.RED + "Do not repeat yourself!");
				event.setCancelled(true);
			}
		} else {
			plugin.chatRepeat.put(playerName, message.toString());
		}

		int counter = 0;

		if (message.length() > 3) {
			if (!player.hasPermission("opticore.bypass.caps"))
				for (int i = 3; i<message.length(); i++) {
					if (Character.isUpperCase(message.charAt(i)) && Character.isLetter(message.charAt(i))) {
						counter++;
					}
				}
			if (counter > (message.length()*0.30)) {
				message = message.toLowerCase();
				char[] msg = message.toCharArray();
				msg.toString();
				message = new String(msg);
				event.setMessage(message);
			}
		}

		for (Player players : Bukkit.getOnlinePlayers()) {
			if (plugin.deafen.contains(players.getName())) {
				event.getRecipients().remove(players);
			}
		}

		if (plugin.deafen.contains(playerName)) {
			event.setCancelled(true);
		}

		World world = event.getPlayer().getWorld();
		if (plugin.localChannel.contains(player.getName())) {
			for (Iterator<Player> itr = event.getRecipients().iterator(); itr.hasNext();) {
				if (!itr.next().getPlayer().getWorld().equals(world)) {
					itr.remove();
				}
			}
		}

		if (plugin.msgChannel.containsKey(playerName)) {

			@SuppressWarnings("deprecation")
			Player target = Bukkit.getPlayer(plugin.msgChannel.get(playerName));

			if (target != null) {
				if (!plugin.deafen.contains(target.getName())) {
					
					event.setCancelled(true);

					target.sendMessage(ChatColor.LIGHT_PURPLE + "From " + playerName + ": " + message);
					player.sendMessage(ChatColor.LIGHT_PURPLE + "To " + target.getName() + ": " + message);

				} else {
					player.sendMessage(ChatColor.RED + "This player is deafened and won't receive your message.");
				}
			}
		}

		for (String player1 : plugin.toggleAlerts) {

			@SuppressWarnings("deprecation")
			Player alertPlayer = Bukkit.getServer().getPlayer(player1);

			Player sender = event.getPlayer();

			if (alertPlayer != null) {
				if (message.toLowerCase().contains(alertPlayer.getName().toLowerCase())) {
					if (sender != alertPlayer) {
						if (plugin.localChannel.contains(sender.getName()) && sender.getWorld() != alertPlayer.getWorld()) {
							return;
						}

						alertPlayer.sendMessage(ChatColor.GREEN + sender.getName() + " mentioned you.");
						methods.playAlertSound(alertPlayer, alertPlayer.getLocation());
					}
				}
			}
		}
	}
}
