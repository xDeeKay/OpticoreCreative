package net.dkcraft.opticore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.InventoryGUI;
import net.dkcraft.opticore.util.Syntax;

public class ToggleNotifications implements CommandExecutor {

	private Main plugin;

	public InventoryGUI inventorygui = new InventoryGUI(plugin);

	public ToggleNotifications(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("togglenotifications") || cmd.getName().equalsIgnoreCase("tn")) {
			if (args.length == 0) {
				Player player = (Player) cs;
				String playerName = player.getName();
				if (!plugin.toggleNotifications.contains(playerName)) {
					plugin.toggleNotifications.add(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled notifications off.");
				} else {
					plugin.toggleNotifications.remove(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled notifications on.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/togglenotifications");
			}
		}
		return true;
	}
}
