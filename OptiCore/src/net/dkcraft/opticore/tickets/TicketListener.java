package net.dkcraft.opticore.tickets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.dkcraft.opticore.Main;

public class TicketListener implements Listener {
	
	public Main plugin;
	private TicketMethods ticket;

	public TicketListener(Main plugin) {
		this.plugin = plugin;
		this.ticket = this.plugin.ticket;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (player.hasPermission("opticore.ticket.staff")) {
			player.setScoreboard(ticket.getScoreboard());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if (ticket.hasTicket(player)) {
			ticket.deleteTicket(player);
			
			if (ticket.isClaimed(player)) {
				plugin.claimedTicket.remove(player.getName());
			}
			
			Bukkit.broadcast(ChatColor.YELLOW + player.getName() + "'s " + ChatColor.GREEN + "ticket was automatically deleted.", "opticore.ticket.staff");
		}
	}
}
