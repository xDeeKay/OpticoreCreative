package net.dkcraft.opticore.spleef;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spleef implements CommandExecutor {

	public Main plugin;
	private SpleefMethods spleef;
	private SpleefRunnable spleefRunnable;

	public Spleef(Main plugin) {
		this.plugin = plugin;
		this.spleef = this.plugin.spleef;
		this.spleefRunnable = this.plugin.spleefRunnable;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("spleef")) {
			if (args.length == 0) {
				if (!spleef.isInQueue(player)) {
					if (spleef.getQueueSize() == 0) {
						spleef.addPlayerToQueue(player);
						player.sendMessage(ChatColor.GREEN + "Need minimum " + ChatColor.YELLOW + "1" + ChatColor.GREEN + " more player to start.");
					} else if (spleef.getQueueSize() == 1) {
						spleef.addPlayerToQueue(player);
						spleef.sendQueuePlayerMessage(spleef.playerJoinQueueMessage(player));

						if (plugin.spleefGame.size() == 0) {
							for (String spleefPlayers : plugin.spleefQueue) {
								@SuppressWarnings("deprecation")
								Player players = Bukkit.getServer().getPlayer(spleefPlayers);

								players.sendMessage(ChatColor.GREEN + "Spleef game will start in " + ChatColor.YELLOW + spleef.timeToStart() + ChatColor.GREEN + " seconds.");
							}
							//start SpleefRunnable
							spleefRunnable.startCountDown(spleef.timeToStart() * 20);
							
						} else {
							for (String spleefPlayers : plugin.spleefQueue) {
								@SuppressWarnings("deprecation")
								Player players = Bukkit.getServer().getPlayer(spleefPlayers);

								players.sendMessage(ChatColor.RED + "A spleef game is already in progress. Your game will start shortly.");
							}
						}
					} else if (spleef.getQueueSize() == 2) {
						spleef.addPlayerToQueue(player);
						spleef.sendQueuePlayerMessage(spleef.playerJoinQueueMessage(player));

					} else if (spleef.getQueueSize() == 3) {
						spleef.addPlayerToQueue(player);
						spleef.sendQueuePlayerMessage(spleef.playerJoinQueueMessage(player));

						if (spleef.getGameSize() == 0) {
							//canel current runnable
							spleefRunnable.stopCountDown();

							//instantly start new runnable
							spleefRunnable.startCountDown(0);

						} else {
							for (String spleefPlayers : plugin.spleefQueue) {
								@SuppressWarnings("deprecation")
								Player players = Bukkit.getServer().getPlayer(spleefPlayers);

								players.sendMessage(ChatColor.RED + "A spleef game is already in progress. Your game will start shortly.");
							}
						}
					} else if (spleef.getQueueSize() == 4) {
						player.sendMessage(ChatColor.RED + "The spleef queue is full, please try again soon.");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You are already in the spleef queue");
				}
			} else {
				player.sendMessage(Syntax.USAGE_INCORRECT + "/spleef");
			}
		}
		return true;
	}
}