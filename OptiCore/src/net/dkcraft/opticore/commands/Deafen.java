package net.dkcraft.opticore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Deafen implements CommandExecutor {

	private Main plugin;

	public Deafen(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("deafen")) {
			if (args.length == 0) {
				Player player = (Player) cs;
				String playerName = player.getName();
				if (!plugin.deafen.contains(playerName)) {
					plugin.deafen.add(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled chat off.");
				} else {
					plugin.deafen.remove(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled chat on.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/deafen");
			}
		}
		return true;
	}
}
