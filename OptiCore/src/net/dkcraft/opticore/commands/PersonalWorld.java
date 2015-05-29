package net.dkcraft.opticore.commands;

import java.util.List;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PersonalWorld implements CommandExecutor {

	private Main plugin;

	public PersonalWorld(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("personalworld") || cmd.getName().equalsIgnoreCase("pw")) {
			return personalWorld(cs, args);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean personalWorld(CommandSender cs, String[] args) {
		if (args.length == 0) {
			cs.sendMessage(Syntax.USAGE_INCORRECT + "/personalworld create/addmember/removemember/listmembers");
			return true;
		}
		if (args.length == 1) {
			if ((args[0].equalsIgnoreCase("listmembers")) || (args[0].equalsIgnoreCase("lm"))) {
				if (cs.hasPermission("opticore.personalworld.removemember")) {
					if (this.plugin.getConfig().contains("PersonalWorlds." + cs.getName().toLowerCase())) {
						if (!this.plugin.getConfig().getStringList("PersonalWorlds." + cs.getName().toLowerCase() + ".Members").isEmpty()) {
							StringBuilder sb = new StringBuilder();
							List<String> list = this.plugin.getConfig().getStringList("PersonalWorlds." + cs.getName().toLowerCase() + ".Members");
							for (String playerlist : list) {
								sb.append(playerlist).append(", ");
							}
							cs.sendMessage(ChatColor.GREEN + "Personal World Members: " + sb.toString().substring(0, sb.length() - 2));
						} else {
							cs.sendMessage(ChatColor.GREEN + "Personal World Members: (none)");
						}
					}
					else cs.sendMessage(ChatColor.RED + "You don't own a personal world.");
				}
				else
					cs.sendMessage("You do not have permission to do that.");
			} else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
				if (cs.hasPermission("opticore.personalworld.create")) {
					cs.sendMessage(Syntax.USAGE_INCORRECT + "/personalworld create <player>");
				} else {
					cs.sendMessage("You do not have permission to do that.");
				}
			} else if (args[0].equalsIgnoreCase("addmember") || args[0].equalsIgnoreCase("am")) {
				if (cs.hasPermission("opticore.personalworld.addmember")) {
					cs.sendMessage(Syntax.USAGE_INCORRECT + "/personalworld addmember <player>");
				} else {
					cs.sendMessage("You do not have permission to do that.");
				}
			} else if (args[0].equalsIgnoreCase("removemember") || args[0].equalsIgnoreCase("rm")) {
				if (cs.hasPermission("opticore.personalworld.removemember")) {
					cs.sendMessage(Syntax.USAGE_INCORRECT + "/personalworld removemember <player>");
				} else {
					cs.sendMessage("You do not have permission to do that.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/personalworld create/addmember/removemember/listmembers");
			}
		} else if (args.length == 2) {
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(args[1]);
			String uuid = player.getUniqueId().toString();
			if ((args[0].equalsIgnoreCase("create")) || (args[0].equalsIgnoreCase("c"))) {
				if (cs.hasPermission("opticore.personalworld.create")) {
					if (!this.plugin.getConfig().contains("PersonalWorlds." + args[1].toLowerCase())) {
						this.plugin.getConfig().set("PersonalWorlds." + args[1].toLowerCase() + ".Owner", args[1].toLowerCase());
						this.plugin.getConfig().set("PersonalWorlds." + args[1].toLowerCase() + ".Members", "");
						this.plugin.saveConfig();
						cs.sendMessage(ChatColor.GREEN + "Created a personal world config for " + args[1].toLowerCase() + ".");
					} else {
						cs.sendMessage(ChatColor.RED + "A personal world config for this player already exists.");
					}
				} else {
					cs.sendMessage("You do not have permission to do that.");
				}
			} else if (args[0].equalsIgnoreCase("addmember") || args[0].equalsIgnoreCase("am")) {
				if (cs.hasPermission("opticore.personalworld.addmember")) {
					if (this.plugin.getConfig().contains("PersonalWorlds." + cs.getName().toLowerCase())) {
						if (!this.plugin.getConfig().getStringList("PersonalWorlds." + cs.getName().toLowerCase() + ".Members").contains(uuid)) {
							List<String> list = this.plugin.getConfig().getStringList("PersonalWorlds." + cs.getName().toLowerCase() + ".Members");
							list.add(uuid);
							this.plugin.getConfig().set("PersonalWorlds." + cs.getName().toLowerCase() + ".Members", list);
							plugin.saveConfig();
							cs.sendMessage(ChatColor.GREEN + "Added " + args[1].toLowerCase() + " as a member to your personal world.");
						} else {
							cs.sendMessage(ChatColor.RED + "This player is already a member of your personal world.");
						}
					} else {
						cs.sendMessage(ChatColor.RED + "You don't own a personal world.");
					}
				} else {
					cs.sendMessage("You do not have permission to do that.");
				}
			} else if (args[0].equalsIgnoreCase("removemember") || args[0].equalsIgnoreCase("rm")) {
				if (cs.hasPermission("opticore.personalworld.removemember")) {
					if (this.plugin.getConfig().contains("PersonalWorlds." + cs.getName().toLowerCase())) {
						if (!this.plugin.getConfig().getStringList("PersonalWorlds." + cs.getName().toLowerCase() + ".Members").isEmpty()) {
							if (this.plugin.getConfig().getList("PersonalWorlds." + cs.getName().toLowerCase() + ".Members").contains(uuid)) {
								List<String> list = this.plugin.getConfig().getStringList("PersonalWorlds." + cs.getName().toLowerCase() + ".Members");
								list.remove(uuid);
								this.plugin.getConfig().set("PersonalWorlds." + cs.getName().toLowerCase() + ".Members", list);
								plugin.saveConfig();
								cs.sendMessage(ChatColor.GREEN + "Removed " + args[1].toLowerCase() + " as a member to your personal world.");
							} else {
								cs.sendMessage(ChatColor.RED + "This player is not a member of your personal world.");
							}
						} else {
							cs.sendMessage(ChatColor.RED + "This player is not a member of your personal world.");
						}
					} else {
						cs.sendMessage(ChatColor.RED + "You don't own a personal world.");
					}
				} else {
					cs.sendMessage("You do not have permission to do that.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/personalworld create/addmember/removemember/listmembers");
			}
		} else if (args.length <= 3) {
			cs.sendMessage(Syntax.USAGE_INCORRECT + "/personalworld create/addmember/removemember/listmembers");
		}
		return true;
	}
}