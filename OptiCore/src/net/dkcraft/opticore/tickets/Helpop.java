package net.dkcraft.opticore.tickets;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.opticore.Main;

public class Helpop implements CommandExecutor {

	public Main plugin;
	private TicketMethods ticket;

	public Helpop(Main plugin) {
		this.plugin = plugin;
		this.ticket = this.plugin.ticket;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("helpop")) {
			if (args.length > 0) {
				
				Player player = (Player) sender;
				String message = StringUtils.join(args, ' ', 0, args.length);

				if (!ticket.hasTicket(player)) {
					ticket.createTicket(player, message);
					ticket.notifyStaff(message, player);
					ticket.sendQueueMessage(player);
				} else if (ticket.isClaimed(player)) {
					sender.sendMessage(ChatColor.RED + "Your ticket has been claimed, please wait.");
				} else {
					sender.sendMessage(ChatColor.RED + "You have already submitted a ticket, please wait.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Usage: /helpop <message>");
			}
		}
		return true;
	}
}
