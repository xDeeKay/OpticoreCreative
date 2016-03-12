package net.dkcraft.opticore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Channel implements CommandExecutor {

	private Main plugin;

	public Channel(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player player = (Player) cs;
			if (cmd.getName().equalsIgnoreCase("channel") || cmd.getName().equalsIgnoreCase("ch")) {
				return channel(player, args);
			}
		}
		return true;
	}

	private boolean channel(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(Syntax.USAGE_INCORRECT + "/channel global/world");
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("global") || args[0].equalsIgnoreCase("g")) {
				
				if (plugin.localChannel.contains(player.getName())) {
					plugin.localChannel.remove(player.getName());
					player.sendMessage(ChatColor.GREEN + "Now speaking in the global channel.");
					
				} else if (plugin.msgChannel.containsKey(player.getName())) {
					plugin.msgChannel.remove(player.getName());
					player.sendMessage(ChatColor.GREEN + "Now speaking in the global channel.");
					
				} else {
					player.sendMessage(ChatColor.RED + "You are already speaking in the global channel.");
				}
			} else if (args[0].equalsIgnoreCase("world") || args[0].equalsIgnoreCase("w") || args[0].equalsIgnoreCase("local") || args[0].equalsIgnoreCase("l")) {
				if (!plugin.localChannel.contains(player.getName())) {
					plugin.localChannel.add(player.getName());
					player.sendMessage(ChatColor.GREEN + "Now speaking in the world channel.");
				} else {
					player.sendMessage(ChatColor.RED + "You are already speaking in the world channel.");
				}
			} else {
				player.sendMessage(Syntax.USAGE_INCORRECT + "/channel global/world");
			}
		} else {
			player.sendMessage(Syntax.USAGE_INCORRECT + "/channel global/world");
		}
		return true;
	}
}
