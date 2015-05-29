package net.dkcraft.opticore.commands;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class R implements CommandExecutor {

	private Main plugin;

	public R(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("r") || cmd.getName().equalsIgnoreCase("reply")) {
			if (args.length > 0) {
				Player player = (Player)cs;
				String target = null;
				String message = StringUtils.join(args, ' ', 0, args.length);
				if (plugin.msg.containsKey(cs.getName())) {
					target = (String)plugin.msg.get(cs.getName());
					if (target != null) {
						if (!plugin.deafen.contains(target)) {
							if (!plugin.deafen.contains(player.getName())) {
								String msgchecked = message.toString();
								if (msgchecked != null) {
									Player targetPlayer = cs.getServer().getPlayer(target);
									player.sendMessage(ChatColor.LIGHT_PURPLE + "To " + targetPlayer.getName() + ": " + message);
									targetPlayer.sendMessage(ChatColor.LIGHT_PURPLE + "From " + player.getName() + ": " + message);
									plugin.msg.put(player.getName(), targetPlayer.getName());
									plugin.msg.put(targetPlayer.getName(), player.getName());
									for (Player p : Bukkit.getOnlinePlayers()) {
										if (plugin.socialSpy.contains(p.getName())) {
											p.sendMessage(ChatColor.GRAY + "From " + player.getName() + " to " + targetPlayer.getName() + ": " + message);
										}
									}
								} else {
									cs.sendMessage(Syntax.USAGE_INCORRECT + "/r <message>");
								}
							}
						} else {
							cs.sendMessage(ChatColor.RED + "This player is deafened and won't receive your message.");
						}
					} else {
						cs.sendMessage(ChatColor.RED + "You can't message an offline player.");
					}
				} else {
					cs.sendMessage(ChatColor.RED + "You have no one to reply to.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/r <message>");
			}
		}
		return true;
	}
}