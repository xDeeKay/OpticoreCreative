package net.dkcraft.opticore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Paint implements CommandExecutor {

	private Main plugin;

	public Paint(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("paint") || cmd.getName().equalsIgnoreCase("optipaint")) {
			if (args.length == 0) {
				Player player = (Player) cs;
				String playerName = player.getName();
				if (!plugin.paint.contains(playerName)) {
					plugin.paint.add(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled paint on.");
				} else {
					plugin.paint.remove(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled paint off.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/paint");
			}
		}
		return true;
	}
}
