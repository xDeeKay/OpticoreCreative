package net.dkcraft.opticore.tickets;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ticket implements CommandExecutor {

	public Main plugin;
	private TicketMethods ticket;

	public Ticket(Main plugin) {
		this.plugin = plugin;
		this.ticket = this.plugin.ticket;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ticket")) {

			Player player = (Player) sender;

			if (args.length == 1) {

				if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
					if (!ticket.isEmpty()) {
						ticket.listTickets(player);
					} else {
						player.sendMessage(ChatColor.RED + "There are no tickets to display.");
					}
				}

			} else if (args.length == 2) {

				@SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayer(args[1]);

				if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
					if (player.hasPermission("opticore.ticket.create")) {
						if (!ticket.hasTicket(player)) {
							String message = StringUtils.join(args, ' ', 1, args.length);
							ticket.createTicket(player, message);
							ticket.notifyStaff(message, player);
							ticket.sendQueueMessage(player);
						} else {
							player.sendMessage(ChatColor.RED + "You have already submitted a ticket, please wait.");
						}
					} else {
						player.sendMessage("You do not have permission to do that.");
					}

				} else if (args[0].equalsIgnoreCase("claim") || args[0].equalsIgnoreCase("cl")) {
					if (player.hasPermission("opticore.ticket.claim")) {
						if (target != null && ticket.hasTicket(target)) {
							if (!ticket.isClaimed(target)) {
								ticket.claimTicket(target);
								player.sendMessage(ChatColor.GREEN + "Claimed " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.GREEN + "ticket.");
							} else {
								player.sendMessage(ChatColor.RED + "This ticket has already been claimed.");
							}
						} else {
							player.sendMessage(ChatColor.RED + "This player has no ticket.");
						}
					} else {
						player.sendMessage("You do not have permission to do that.");
					}

				} else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("d")) {
					if (player.hasPermission("opticore.ticket.delete")) {
						if (target != null && ticket.hasTicket(target)) {
							ticket.deleteTicket(target);
							if (ticket.isClaimed(target)) {
								plugin.claimedTicket.remove(target.getName());
							}
							player.sendMessage(ChatColor.GREEN + "Deleted " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.GREEN + "ticket.");
						} else {
							player.sendMessage(ChatColor.RED + "This player has no ticket.");
						}
					} else {
						player.sendMessage("You do not have permission to do that.");
					}

				} else if (args[0].equalsIgnoreCase("view") || args[0].equalsIgnoreCase("v")) {
					if (player.hasPermission("opticore.ticket.view")) {
						if (target != null && ticket.hasTicket(target)) {
							ticket.viewTicket(player, target);
						} else {
							player.sendMessage(ChatColor.RED + "This player has no ticket.");
						}
					} else {
						player.sendMessage("You do not have permission to do that.");
					}

				} else if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
					if (player.hasPermission("opticore.ticket.teleport")) {
						if (target != null && ticket.hasTicket(target)) {
							if (!ticket.isClaimed(target)) {
								ticket.teleportTicket(player, target);
								ticket.claimTicket(target);
								player.sendMessage(ChatColor.GREEN + "Claimed " + ChatColor.YELLOW + target.getName() + "'s " + ChatColor.GREEN + "ticket.");
							} else {
								player.sendMessage(ChatColor.RED + "This ticket has already been claimed.");
							}
						} else {
							player.sendMessage(ChatColor.RED + "This player has no ticket.");
						}
					} else {
						player.sendMessage("You do not have permission to do that.");
					}
				} else {
					player.sendMessage(Syntax.USAGE_INCORRECT + "/ticket list/create/claim/delete/view/teleport");
				}
			} else if (args.length > 2) {
				if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
					if (player.hasPermission("opticore.ticket.create")) {
						if (!ticket.hasTicket(player)) {
							String message = StringUtils.join(args, ' ', 1, args.length);
							ticket.createTicket(player, message);
							ticket.notifyStaff(message, player);
							ticket.sendQueueMessage(player);
						} else {
							player.sendMessage(ChatColor.RED + "You have already submitted a ticket, please wait.");
						}
					} else {
						player.sendMessage("You do not have permission to do that.");
					}

				}
			}
		}
		return true;
	}
}