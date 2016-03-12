package net.dkcraft.opticore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class Msg implements CommandExecutor {

	private Main plugin;

	public Msg(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("msg") || cmd.getName().equalsIgnoreCase("tell") || cmd.getName().equalsIgnoreCase("m") || cmd.getName().equalsIgnoreCase("t") || cmd.getName().equalsIgnoreCase("whisper")) {

			if (args.length == 1) {

				Player target = Bukkit.getPlayer(args[0]);

				if (target != null) {
					if (target != cs) {
						
						plugin.msgChannel.put(cs.getName(), target.getName());
						cs.sendMessage(ChatColor.GREEN + "You are now privately messaging " + target.getName());
						cs.sendMessage(ChatColor.GREEN + "Use '/channel global' to return to the regular chat");
					} else {
						cs.sendMessage(ChatColor.RED + "You can't message yourself.");
					}
				} else {
					cs.sendMessage(ChatColor.RED + "You can't message an offline player.");
				}

			} else if (args.length > 1) {

				String msgSender = cs.getName();
				Player target = Bukkit.getPlayer(args[0]);
				String message = StringUtils.join(args, ' ', 1, args.length);

				if (target != null) {
					if (target != cs) {
						if (!plugin.deafen.contains(target.getName())) {
							if (!plugin.deafen.contains(msgSender)) {
								target.sendMessage(ChatColor.LIGHT_PURPLE + "From " + msgSender + ": " + message);
								cs.sendMessage(ChatColor.LIGHT_PURPLE + "To " + target.getName() + ": " + message);
								plugin.msg.put(msgSender, target.getName());
								plugin.msg.put(target.getName(), msgSender);
								for (Player p : Bukkit.getOnlinePlayers()) {
									if (plugin.socialSpy.contains(p.getName())) {
										p.sendMessage(ChatColor.GRAY + "From " + msgSender + " to " + target.getName() + ": " + message);
									}
								}
							}
						} else {
							cs.sendMessage(ChatColor.RED + "This player is deafened and won't receive your message.");
						}
					} else {
						cs.sendMessage(ChatColor.RED + "You can't message yourself.");
					}
				} else {
					cs.sendMessage(ChatColor.RED + "You can't message an offline player.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/msg <player> <message>");
			}
		}
		return true;
	}
}
