package net.dkcraft.opticore.commands;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleAlerts implements CommandExecutor {

	private Main plugin;

	public ToggleAlerts(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("togglealerts") || cmd.getName().equalsIgnoreCase("ta")) {
			if (args.length == 0) {
				Player player = (Player) cs;
				String playerName = player.getName();
				if (!plugin.toggleAlerts.contains(playerName)) {
					plugin.toggleAlerts.add(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled alerts on.");
				} else {
					plugin.toggleAlerts.remove(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled alerts off.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/togglealerts");
			}
		}
		return true;
	}
}