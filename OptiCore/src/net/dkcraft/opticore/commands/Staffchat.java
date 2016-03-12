package net.dkcraft.opticore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Staffchat implements CommandExecutor {

	private Main plugin;

	public Staffchat(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("staffchat") || (cmd.getName().equalsIgnoreCase("sc") || (cmd.getName().equalsIgnoreCase("staff")))) {
			if (args.length == 0) {
				Player player = (Player) cs;
				String playerName = player.getName();
				if (!plugin.staffChat.contains(playerName)) {
					plugin.staffChat.add(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled staff chat on.");
				} else {
					plugin.staffChat.remove(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled staff chat off.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/staffchat");
			}
		}
		return true;
	}
}
