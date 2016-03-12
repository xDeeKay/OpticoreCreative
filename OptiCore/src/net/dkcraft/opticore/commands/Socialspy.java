package net.dkcraft.opticore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Socialspy implements CommandExecutor {

	private Main plugin;

	public Socialspy(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("socialspy")) {
			if (args.length == 0) {
				Player player = (Player) cs;
				String playerName = player.getName();
				if (!plugin.socialSpy.contains(playerName)) {
					plugin.socialSpy.add(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled social spy on.");
				} else {
					plugin.socialSpy.remove(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled social spy off.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/socialspy");
			}
		}
		return true;
	}
}
