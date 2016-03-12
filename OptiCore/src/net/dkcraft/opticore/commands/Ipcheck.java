package net.dkcraft.opticore.commands;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Ipcheck implements CommandExecutor {

	private Main plugin;

	public Ipcheck(Main plugin) {
		this.plugin = plugin;
	}

	FileConfiguration userconfig = null;

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ipcheck")) {
			if (args.length == 1) {
				UUID uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
				File userfile = new File(plugin.getDataFolder() + File.separator + "ip" + File.separator + uuid + ".yml");
				if (userfile.exists()) {
					userconfig = YamlConfiguration.loadConfiguration(userfile);
					if (!userconfig.getStringList("IP").isEmpty()) {
						StringBuilder sb = new StringBuilder();
						List<String> list = userconfig.getStringList("IP");
						for (String playerlist : list) {
							sb.append(playerlist).append(", ");
						}
						cs.sendMessage(ChatColor.YELLOW + args[0] + "'s current IP" + ChatColor.GREEN + ": " + userconfig.getString("CurrentIP"));
						cs.sendMessage(ChatColor.GREEN + "IP's associated with " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ": " + sb.toString().substring(0, sb.length()-2));
					} else {
						cs.sendMessage(ChatColor.RED + "The file for this player/IP is empty. Please contact an Admin!");
					}
				} else {
					cs.sendMessage(ChatColor.RED + "This player/IP does not exist.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/ipcheck <player>");
			}
		}
		return true;
	}
}
