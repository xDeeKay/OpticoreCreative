package net.dkcraft.opticore.stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Stats implements CommandExecutor {

	public Main plugin;
	private MySQL mysql;

	public Stats(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender cs, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("stats")) {
			if (args.length == 1) {
				final String uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();

				if (mysql.playerDataContainsUUID(uuid)) {

					cs.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Fetching " + args[0] + "'s statistics...");

					Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
						public void run(){
							try {
								Statement statement = mysql.connection.createStatement();
								Statement statement2 = mysql.connection.createStatement();
								Statement statement3 = mysql.connection.createStatement();
								Statement statement4 = mysql.connection.createStatement();
								Statement statement5 = mysql.connection.createStatement();
								Statement statement6 = mysql.connection.createStatement();
								Statement statement7 = mysql.connection.createStatement();
								Statement statement8 = mysql.connection.createStatement();
								Statement statement9 = mysql.connection.createStatement();
								Statement statement10 = mysql.connection.createStatement();

								ResultSet resJoinDate = statement.executeQuery("SELECT join_date FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resJoinDate.next();

								ResultSet resLastOnline = statement2.executeQuery("SELECT last_online FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resLastOnline.next();

								ResultSet resTotalLogins = statement3.executeQuery("SELECT total_logins FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resTotalLogins.next();

								ResultSet resTimeOnline = statement4.executeQuery("SELECT time_online FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resTimeOnline.next();

								ResultSet resBlocksPlaced = statement5.executeQuery("SELECT blocks_placed FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resBlocksPlaced.next();

								ResultSet resBlocksBroken = statement6.executeQuery("SELECT blocks_broken FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resBlocksBroken.next();

								ResultSet resLinesSpoken = statement7.executeQuery("SELECT lines_spoken FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resLinesSpoken.next();

								ResultSet resTotalVotes = statement8.executeQuery("SELECT total_votes FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resTotalVotes.next();

								ResultSet resSpleefWins = statement9.executeQuery("SELECT spleef_wins FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resSpleefWins.next();

								ResultSet resSpleefLosses = statement10.executeQuery("SELECT spleef_losses FROM `player_stats` WHERE uuid= '" + uuid + "';");
								resSpleefLosses.next();

								cs.sendMessage(ChatColor.GREEN + args[0] + "'s statistics:");

								if (resJoinDate.getString("join_date") == null) {
									cs.sendMessage(ChatColor.GREEN + " Join date: " + ChatColor.YELLOW + "null");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Join date: " + ChatColor.YELLOW + resJoinDate.getString("join_date"));
								}

								if (resLastOnline.getString("last_online") == null) {
									cs.sendMessage(ChatColor.GREEN + " Last online: " + ChatColor.YELLOW + "null");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Last online: " + ChatColor.YELLOW + resLastOnline.getString("last_online"));
								}

								if (resTotalLogins.getString("total_logins") == null) {
									cs.sendMessage(ChatColor.GREEN + " Total logins: " + ChatColor.YELLOW + "0");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Total logins: " + ChatColor.YELLOW + resTotalLogins.getInt("total_logins"));
								}

								int input = resTimeOnline.getInt("time_online");
								int numberOfDays;
								int numberOfHours;
								int numberOfMinutes;
								int numberOfSeconds;

								numberOfDays = input / 86400 ;
								numberOfHours = (input % 86400 ) / 3600 ;
								numberOfMinutes = ((input % 86400 ) % 3600 ) / 60 ;
								numberOfSeconds = ((input % 86400 ) % 3600 ) % 60  ;

								if (resTimeOnline.getString("time_online") == null) {
									cs.sendMessage(ChatColor.GREEN + " Time online: " + ChatColor.YELLOW + "null");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Time online: " + ChatColor.YELLOW + numberOfDays + "d, " + numberOfHours + "h, " + numberOfMinutes + "m, " + numberOfSeconds + "s");
								}

								if (resBlocksPlaced.getString("blocks_placed") == null) {
									cs.sendMessage(ChatColor.GREEN + " Blocks placed: " + ChatColor.YELLOW + "0");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Blocks placed: " + ChatColor.YELLOW + resBlocksPlaced.getInt("blocks_placed"));
								}

								if (resBlocksBroken.getString("blocks_broken") == null) {
									cs.sendMessage(ChatColor.GREEN + " Blocks broken: " + ChatColor.YELLOW + "0");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Blocks broken: " + ChatColor.YELLOW + resBlocksBroken.getInt("blocks_broken"));
								}

								if (resLinesSpoken.getString("lines_spoken") == null) {
									cs.sendMessage(ChatColor.GREEN + " Lines spoken: " + ChatColor.YELLOW + "0");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Lines spoken: " + ChatColor.YELLOW + resLinesSpoken.getInt("lines_spoken"));
								}

								if (resTotalVotes.getString("total_votes") == null) {
									cs.sendMessage(ChatColor.GREEN + " Total votes: " + ChatColor.YELLOW + "0");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Total votes: " + ChatColor.YELLOW + resTotalVotes.getInt("total_votes"));
								}

								if (resSpleefWins.getString("spleef_wins") == null) {
									cs.sendMessage(ChatColor.GREEN + " Spleef wins: " + ChatColor.YELLOW + "0");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Spleef wins: " + ChatColor.YELLOW + resSpleefWins.getInt("spleef_wins"));
								}

								if (resSpleefLosses.getString("spleef_losses") == null) {
									cs.sendMessage(ChatColor.GREEN + " Spleef losses: " + ChatColor.YELLOW + "0");
								} else {
									cs.sendMessage(ChatColor.GREEN + " Spleef losses: " + ChatColor.YELLOW + resSpleefLosses.getInt("spleef_losses"));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					cs.sendMessage(ChatColor.RED + "This player does not exist.");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/stats <player>");
			}
		}
		return true;
	}
}