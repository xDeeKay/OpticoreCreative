package net.dkcraft.opticore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Freeze implements CommandExecutor {

	private Main plugin;

	public Freeze(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("freeze")) {
			if (args.length > 0) {
				Player player = Bukkit.getPlayer(args[0]);
				String playerName = player.getName();
				if (player != cs) {
					if (player != null) {
						if (!plugin.freeze.contains(playerName)) {
							String message = "";
							if (args.length > 1) {
								for (int i = 1; i < args.length; ++i) {
									message += args[i] + " ";
								}
								plugin.freeze.add(playerName);
								player.sendMessage(ChatColor.RED + "You have been frozen by a staff member. Reason: " + ChatColor.YELLOW + message);
								cs.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has been frozen.");
							} else {
								plugin.freeze.add(playerName);
								player.sendMessage(ChatColor.RED + "You have been frozen by a staff member.");
								cs.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has been frozen.");
							}
						} else {
							plugin.freeze.remove(playerName);
							player.sendMessage(ChatColor.RED + "You have been unfrozen by a staff member.");
							cs.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has been unfrozen.");
						}
					} else {
						cs.sendMessage(ChatColor.RED + "You can't freeze an offline player.");
					}
				} else {
					cs.sendMessage(ChatColor.RED + "You can't freeze yourself.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/freeze <player> [reason]");
			}
		}
		return true;
	}
}
