package net.dkcraft.opticore.tickets;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.dkcraft.opticore.Main;

public class TicketMethods {
	
	public Main plugin;
	
	private ChatColor RED = ChatColor.RED;
	private ChatColor YELLOW = ChatColor.YELLOW;
	private ChatColor GREEN = ChatColor.GREEN;

	public TicketMethods(Main plugin) {
		this.plugin = plugin;
	}
	
	private Scoreboard scoreboard;

	// Setup scoreboard
	public void setupScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

		Objective tickets = scoreboard.registerNewObjective("tickets", "dummy");
		tickets.setDisplayName("Tickets");
		tickets.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	// Get Scoreboard
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
	
	// List tickets
	public void listTickets(Player sender) {
		sender.sendMessage(GREEN + "There are " + YELLOW + getTicketsSize() + GREEN + " active tickets.");
		int count = 1;
		for (Entry<String, String> entry : plugin.tickets.entrySet()) {
			sender.sendMessage(GREEN + "" + count++ + ". " + YELLOW + entry.getKey() + ": " + RED + entry.getValue());
		}
	}

	// Create ticket
	public void createTicket(Player player, String message) {
		plugin.tickets.put(player.getName(), message);
		getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(RED + player.getName()).setScore(1);
	}
	
	// Send queue message
	public void sendQueueMessage(Player player) {
		player.sendMessage(ChatColor.GREEN + "Ticket submitted. Please wait for a staff members response.");
		player.sendMessage(ChatColor.GREEN + "Queue position: " + YELLOW + getTicketsSize());
	}

	// Claim ticket
	public void claimTicket(Player player) {
		plugin.claimedTicket.add(player.getName());
		getScoreboard().resetScores(RED + player.getName());
		getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(GREEN + player.getName()).setScore(0);
	}
	
	// If ticket is claimed
	public boolean isClaimed(Player player) {
		return plugin.claimedTicket.contains(player.getName());
	}

	// Delete ticket
	public void deleteTicket(Player player) {
		plugin.tickets.remove(player.getName());
		getScoreboard().resetScores(RED + player.getName());
		getScoreboard().resetScores(GREEN + player.getName());
	}

	// View ticket
	public void viewTicket(Player sender, Player player) {
		sender.sendMessage(YELLOW + player.getName() + "'s " + GREEN +  "current ticket: " + RED + plugin.tickets.get(player.getName()));
	}

	// Teleport ticket
	public void teleportTicket(Player sender, Player player) {
		sender.teleport(player.getLocation());
	}

	// Get ticket amount
	public int getTicketsSize() {
		return plugin.tickets.size();
	}

	// If tickets is empty
	public boolean isEmpty() {
		return plugin.tickets.isEmpty();
	}

	// If player has ticket
	public boolean hasTicket(Player player) {
		return plugin.tickets.containsKey(player.getName());
	}

	// Notify online staff
	public void notifyStaff(String message, Player player) {
		Bukkit.broadcast(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has sumbitted a ticket: " + ChatColor.RED + message, "opticore.ticket.staff");
	}
}
