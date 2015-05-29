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
	private Methods methods;
	private NewSpleefRunnable spleefCountDown;

	public Spleef(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.spleefCountDown = this.plugin.spleefCountDown;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("spleef")) {
			if (args.length == 0) {
				if (!methods.isInQueue(player)) {
					if (methods.getQueueSize() == 0) {
						methods.addPlayerToQueue(player);
						player.sendMessage(ChatColor.GREEN + "Need minimum " + ChatColor.YELLOW + "1" + ChatColor.GREEN + " more player to start.");
					} else if (methods.getQueueSize() == 1) {
						methods.addPlayerToQueue(player);
						methods.sendQueuePlayerMessage(methods.playerJoinQueueMessage(player));

						if (plugin.spleefGame.size() == 0) {
							for (String spleefPlayers : plugin.spleefQueue) {
								@SuppressWarnings("deprecation")
								Player players = Bukkit.getServer().getPlayer(spleefPlayers);

								players.sendMessage(ChatColor.GREEN + "Spleef game will start in " + ChatColor.YELLOW + methods.timeToStart() + ChatColor.GREEN + " seconds.");
							}
							//start SpleefRunnable
							
							spleefCountDown.startCountDown(methods.timeToStart() * 20);
							
							//BukkitTask spleefRunnable = new SpleefRunnable(this.plugin, location, inventory, 2).runTaskTimer(this.plugin, methods.timeToStart() * 20, 0);
							//new SpleefRunnable(this.plugin, location, inventory).runTaskLater(this.plugin, methods.timeToStart() * 20);
							
						} else {
							for (String spleefPlayers : plugin.spleefQueue) {
								@SuppressWarnings("deprecation")
								Player players = Bukkit.getServer().getPlayer(spleefPlayers);

								players.sendMessage(ChatColor.RED + "A spleef game is already in progress. Your game will start shortly.");
							}
						}
					} else if (methods.getQueueSize() == 2) {
						methods.addPlayerToQueue(player);
						methods.sendQueuePlayerMessage(methods.playerJoinQueueMessage(player));

					} else if (methods.getQueueSize() == 3) {
						methods.addPlayerToQueue(player);
						methods.sendQueuePlayerMessage(methods.playerJoinQueueMessage(player));

						if (methods.getGameSize() == 0) {
							//canel current runnable
							spleefCountDown.stopCountDown();

							//instantly start new runnable
							spleefCountDown.startCountDown(0);

						} else {
							for (String spleefPlayers : plugin.spleefQueue) {
								@SuppressWarnings("deprecation")
								Player players = Bukkit.getServer().getPlayer(spleefPlayers);

								players.sendMessage(ChatColor.RED + "A spleef game is already in progress. Your game will start shortly.");
							}
						}
					} else if (methods.getQueueSize() == 4) {
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