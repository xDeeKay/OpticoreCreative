package net.dkcraft.opticore.commands;

import java.io.File;
import java.util.List;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Aliases implements CommandExecutor {

	private Main plugin;

	public Aliases(Main plugin) {
		this.plugin = plugin;
	}

	FileConfiguration userconfig = null;

	@SuppressWarnings({ "deprecation" })
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("aliases")) {
			if (args.length == 1) {
				String uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
				File uuidFile = new File(plugin.getDataFolder() + File.separator + "ip" + File.separator + uuid + ".yml");
				if (uuidFile.exists()) {
					userconfig = YamlConfiguration.loadConfiguration(uuidFile);
					File ipFile = new File(plugin.getDataFolder() + File.separator + "alias" + File.separator + userconfig.getString("CurrentIP") + ".yml");
					userconfig = YamlConfiguration.loadConfiguration(ipFile);
					if (!userconfig.getStringList("Usernames").isEmpty()) {
						StringBuilder sb = new StringBuilder();
						List<String> list2 = userconfig.getStringList("Usernames");
						for (String playerlist : list2) {
							sb.append(playerlist).append(", ");
						}
						userconfig = YamlConfiguration.loadConfiguration(uuidFile);
						cs.sendMessage(ChatColor.GREEN + "Usernames associated with " + ChatColor.YELLOW + userconfig.getString("CurrentIP") + ChatColor.GREEN + ": " + sb.toString().substring(0, sb.length()-2));
					} else {
						cs.sendMessage(ChatColor.RED + "The file for this player/IP is empty. Please contact an Admin!");
					}
				} else {
					cs.sendMessage(ChatColor.RED + "This player/IP does not exist.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/aliases <player>");
			}
		}
		return true;
	}
}