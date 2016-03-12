package net.dkcraft.opticore.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.dkcraft.opticore.Main;

public class ColouredNames implements Listener {

	public Main plugin;
	private Methods methods;

	public ColouredNames(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
	}

	private Team guest;
	private Team recruit;
	private Team builder;
	private Team crafter;
	private Team operator;
	private Team developer;
	private Team admin;
	private Team owner;

	public void runNameTagUpdater() {
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {

				for (Player player : Bukkit.getOnlinePlayers()) {

					Scoreboard sb = player.getScoreboard();

					guest = sb.getTeam("guest");
					recruit = sb.getTeam("recruit");
					builder = sb.getTeam("builder");
					crafter = sb.getTeam("crafter");
					operator = sb.getTeam("operator");
					developer = sb.getTeam("developer");
					admin = sb.getTeam("admin");
					owner = sb.getTeam("owner");

					if (guest == null) {
						guest = sb.registerNewTeam("guest");
						guest.setPrefix(ChatColor.WHITE + "");
					}

					if (recruit == null) {
						recruit = sb.registerNewTeam("recruit");
						recruit.setPrefix(ChatColor.GOLD + "");
					}

					if (builder == null) {
						builder = sb.registerNewTeam("builder");
						builder.setPrefix(ChatColor.GREEN + "");
					}

					if (crafter == null) {
						crafter = sb.registerNewTeam("crafter");
						crafter.setPrefix(ChatColor.DARK_PURPLE + "");
					}

					if (operator == null) {
						operator = sb.registerNewTeam("operator");
						operator.setPrefix(ChatColor.AQUA + "");
					}

					if (developer == null) {
						developer = sb.registerNewTeam("developer");
						developer.setPrefix(ChatColor.DARK_GREEN + "");
					}

					if (admin == null) {
						admin = sb.registerNewTeam("admin");
						admin.setPrefix(ChatColor.BLUE + "");
					}

					if (owner == null) {
						owner = sb.registerNewTeam("owner");
						owner.setPrefix(ChatColor.DARK_RED + "");
					}

					for (Player player2 : Bukkit.getOnlinePlayers()) {

						for (Team teams : sb.getTeams()) {
							if (teams.hasPlayer(player2) == true) {
								teams.removePlayer(player2);
							}
						}

						if (methods.getRank(player2).equalsIgnoreCase("default")) {
							if (!guest.hasPlayer(player2)) {
								guest.addPlayer(player2);
							}
						} else if (methods.getRank(player2).equalsIgnoreCase("recruit")) {
							if (!recruit.hasPlayer(player2)) {
								recruit.addPlayer(player2);
							}
						} else if (methods.getRank(player2).equalsIgnoreCase("builder")) {
							if (!builder.hasPlayer(player2)) {
								builder.addPlayer(player2);
							}
						} else if (methods.getRank(player2).equalsIgnoreCase("crafter")) {
							if (!crafter.hasPlayer(player2)) {
								crafter.addPlayer(player2);
							}
						} else if (methods.getRank(player2).equalsIgnoreCase("operator")) {
							if (!operator.hasPlayer(player2)) {
								operator.addPlayer(player2);
							}
						} else if (methods.getRank(player2).equalsIgnoreCase("developer")) {
							if (!developer.hasPlayer(player2)) {
								developer.addPlayer(player2);
							}
						} else if (methods.getRank(player2).equalsIgnoreCase("admin")) {
							if (!admin.hasPlayer(player2)) {
								admin.addPlayer(player2);
							}
						} else if (methods.getRank(player2).equalsIgnoreCase("owner")) {
							if (!owner.hasPlayer(player2)) {
								owner.addPlayer(player2);
							}
						} else {
							if (!guest.hasPlayer(player2)) {
								guest.addPlayer(player2);
							}
						}
					}
				}
			}
		}.run();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		runNameTagUpdater();
	}
}
