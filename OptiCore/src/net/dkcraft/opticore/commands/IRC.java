package net.dkcraft.opticore.commands;

import net.dkcraft.opticore.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IRC implements CommandExecutor {

	public Main plugin;

	public IRC(Main plugin) {
		this.plugin = plugin;
	}

	public void commandUsage(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "Available IRC Commands:");
		sender.sendMessage(ChatColor.GREEN + "/irc kick <user>");
		sender.sendMessage(ChatColor.GREEN + "/irc ban <user>");
		sender.sendMessage(ChatColor.GREEN + "/irc unban <user>");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("irc")) {
			if (args.length > 2) {
				commandUsage(sender);
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("kick")) {
					if (sender.hasPermission("opticore.irc.kick")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ircraw 0 KICK #opticraft-creative " + args[1]);
						sender.sendMessage(ChatColor.GREEN + "Kicked " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " from the IRC channel.");
					} else {
						sender.sendMessage("You do not have permission to do that.");
					}
				} else if (args[0].equalsIgnoreCase("ban")) {
					if (sender.hasPermission("opticore.irc.ban")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ircraw 0 MODE #opticraft-creative +b " + args[1] + "!*@*");
						sender.sendMessage(ChatColor.GREEN + "Bann " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " from the IRC channel.");
					} else {
						sender.sendMessage("You do not have permission to do that.");
					}
				} else if (args[0].equalsIgnoreCase("unban")) {
					if (sender.hasPermission("opticore.irc.unban")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ircraw 0 MODE #opticraft-creative -b " + args[1] + "!*@*");
						sender.sendMessage(ChatColor.GREEN + "Unbanned " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " from the IRC channel.");
					} else {
						sender.sendMessage("You do not have permission to do that.");
					}
				} else {
					commandUsage(sender);
				}
			} else {
				commandUsage(sender);
			}
		}
		return true;
	}
}