package net.dkcraft.opticore.spleef;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class SpleefManage implements CommandExecutor {

	public Main plugin;
	private Methods methods;
	private NewSpleefRunnable spleefCountDown;
	//private Location location;
	//private Inventory inventory;
	
	public SpleefManage(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.spleefCountDown = this.plugin.spleefCountDown;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spleefmanage") || cmd.getName().equalsIgnoreCase("sm")) {
			return spleefManage(cs, args);
		}
		return true;
	}

	private boolean spleefManage(CommandSender cs, String[] args) {
		if (args.length == 0) {
			cs.sendMessage(ChatColor.GREEN + "Spleef Manage:");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage world <world name>");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage timer <int in seconds>");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage floor <block/corner1/corner2>");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage pit <corner1/corner2>");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage players <player1/player2/player3/player4>");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage forcestart");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage forcestop");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage clearqueue");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage cleargame");
			cs.sendMessage(ChatColor.GREEN + " /spleefmanage resetfloor");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("world")) {
				cs.sendMessage(ChatColor.GREEN + "Use '/spleefmanage world <world name>' to set the arena world.");
			} else if (args[0].equalsIgnoreCase("timer")) {
				cs.sendMessage(ChatColor.GREEN + "Use '/spleefmanage timer <int in seconds>' to set the timer.");
			} else if (args[0].equalsIgnoreCase("floor")) {
				cs.sendMessage(ChatColor.GREEN + "Use '/spleefmanage floor <option>' to set the floor options.");
				cs.sendMessage(ChatColor.GREEN + "Possible 'floor' options: block, corner1, corner2");
			} else if (args[0].equalsIgnoreCase("pit")) {
				cs.sendMessage(ChatColor.GREEN + "Use '/spleefmanage pit <option>' and punch a block to set the pit location.");
				cs.sendMessage(ChatColor.GREEN + "Possible 'pit' options: corner1, corner2");
			} else if (args[0].equalsIgnoreCase("players")) {
				cs.sendMessage(ChatColor.GREEN + "Use '/spleefmanage players <option>' and stand in a position to set the player location.");
				cs.sendMessage(ChatColor.GREEN + "Possible 'players' options: player1, player2, player3, player4");

			} else if (args[0].equalsIgnoreCase("forcestart")) {
				if (methods.getQueueSize() >= 2 && methods.getGameSize() == 0) {

					//cancel current runnable
					//Bukkit.getScheduler().cancelAllTasks();
					spleefCountDown.stopCountDown();
					
					//instantly start new runnable
					//new SpleefRunnable(this.plugin, location, inventory).run();
					spleefCountDown.startCountDown(0);
					cs.sendMessage(ChatColor.GREEN + "Force started the game.");

				} else {
					cs.sendMessage(ChatColor.RED + "Not enough players in queue or too many players in game.");
				}

			} else if (args[0].equalsIgnoreCase("forcestop")) {
				if (methods.getGameSize() >= 1) {
					for (String spleefPlayers : plugin.spleefGame) {
						@SuppressWarnings("deprecation")
						Player players = Bukkit.getServer().getPlayer(spleefPlayers);

						Location location = plugin.spleefLocation.get(spleefPlayers);
						PlayerInventory inventory = players.getInventory();
						methods.preparePlayerFinish(players, location, inventory);
						methods.resetFloor();
					}
					methods.clearGame();
					cs.sendMessage(ChatColor.GREEN + "Force stopped the game.");
				} else {
					cs.sendMessage(ChatColor.RED + "Not enough players in game.");
				}

			} else if (args[0].equalsIgnoreCase("clearqueue")) {
				if (methods.getQueueSize() >= 1) {
					methods.clearQueue();
					cs.sendMessage(ChatColor.GREEN + "Cleared the queue.");
				} else {
					cs.sendMessage(ChatColor.RED + "Not enough players in queue.");
				}

			} else if (args[0].equalsIgnoreCase("cleargame")) {
				if (methods.getGameSize() >= 1) {
					methods.clearGame();
					cs.sendMessage(ChatColor.GREEN + "Cleared the game.");
				} else {
					cs.sendMessage(ChatColor.RED + "Not enough players in game.");
				}

			} else if (args[0].equalsIgnoreCase("resetfloor")) {
				methods.resetFloor();
				cs.sendMessage(ChatColor.GREEN + "Reset floor.");
			}

		} else if (args.length == 2) {
			Player player = (Player) cs;
			String playerName = player.getName();
			Location location = player.getLocation();
			String entry = args[1];

			if (args[0].equalsIgnoreCase("world")) {
				plugin.getConfig().set("spleef.world", entry);
				plugin.saveConfig();
				cs.sendMessage(ChatColor.GREEN + "Set spleef world.");
			} else if (args[0].equalsIgnoreCase("timer")) {
				if (methods.isInt(entry)) {
					plugin.getConfig().set("spleef.timer", Integer.parseInt(entry));
					plugin.saveConfig();
					cs.sendMessage(ChatColor.GREEN + "Set spleef timer.");
				} else {
					cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage timer <int in seconds>");
				}
			} else if (args[0].equalsIgnoreCase("floor")) {
				if (args[1].equalsIgnoreCase("corner1") || args[1].equalsIgnoreCase("corner2")) {
					if (plugin.spleefFloorManage.containsKey(playerName)) {
						cs.sendMessage(ChatColor.GREEN + "Action in progress. Break a block to set floor " + plugin.spleefFloorManage.get(playerName) + ".");
					} else {
						plugin.spleefFloorManage.put(playerName, entry);
						cs.sendMessage(ChatColor.GREEN + "Break a block to set floor " + entry + ".");
					}
				} else {
					cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage floor <corner1/corner2>");
				}
			} else if (args[0].equalsIgnoreCase("pit")) {
				if (args[1].equalsIgnoreCase("corner1") || args[1].equalsIgnoreCase("corner2")) {
					if (plugin.spleefPitManage.containsKey(playerName)) {
						cs.sendMessage(ChatColor.GREEN + "Action in progress. Break a block to set pit " + plugin.spleefPitManage.get(playerName) + ".");
					} else {
						plugin.spleefPitManage.put(playerName, entry);
						cs.sendMessage(ChatColor.GREEN + "Break a block to set pit " + entry + ".");
					}
				} else {
					cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage pit <corner1/corner2>");
				}
			} else if (args[0].equalsIgnoreCase("players")) {
				if (args[1].equalsIgnoreCase("player1") || args[1].equalsIgnoreCase("player2") || args[1].equalsIgnoreCase("player3") || args[1].equalsIgnoreCase("player4")) {
					plugin.getConfig().set("spleef.players." + entry + ".x", location.getX());
					plugin.getConfig().set("spleef.players." + entry + ".y", location.getY());
					plugin.getConfig().set("spleef.players." + entry + ".z", location.getZ());
					plugin.getConfig().set("spleef.players." + entry + ".yaw", location.getYaw());
					plugin.getConfig().set("spleef.players." + entry + ".pitch", location.getPitch());
					plugin.saveConfig();
					cs.sendMessage(ChatColor.GREEN + "Set " + entry + " location.");
				} else {
					cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage players <player1/player2/player3/player4>");
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage world/timer/floor/pit/players");
			}
		} else if (args.length == 3) {
			String block = args[2];
			if (args[0].equalsIgnoreCase("floor")) {
				if (args[1].equalsIgnoreCase("block")) {
					if (block.contains(":")) {
						String[] parts = block.split(":");
						String blockType = parts[0];
						String blockData = parts[1];
						//int partsLength = parts.length;

						if (methods.isInt(blockType) && methods.isInt(blockData)) {
							plugin.getConfig().set("spleef.floor.blocktype", Integer.parseInt(blockType));
							plugin.getConfig().set("spleef.floor.blockdata", Integer.parseInt(blockData));
							plugin.saveConfig();
							cs.sendMessage(ChatColor.GREEN + "Set spleef floor block.");
						} else {
							cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage floor block <block int id>");
						}
					} else {
						if (methods.isInt(block)) {
							plugin.getConfig().set("spleef.floor.blocktype", Integer.parseInt(block));
							plugin.getConfig().set("spleef.floor.blockdata", 0);
							plugin.saveConfig();
							cs.sendMessage(ChatColor.GREEN + "Set spleef floor block.");
						} else {
							cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage floor block <block int id>");
						}
					}
				} else if (args[1].equalsIgnoreCase("rainbow")) {
					if (args[2].equalsIgnoreCase("on")) {
						plugin.getConfig().set("spleef.floor.rainbow", true);
						plugin.saveConfig();
						cs.sendMessage(ChatColor.DARK_RED + "R" + ChatColor.RED + "a" + ChatColor.GOLD + "i" + ChatColor.YELLOW + "n" + ChatColor.DARK_GREEN + "b" + ChatColor.GREEN + "o" + ChatColor.BLUE + "w " + ChatColor.AQUA + "o" + ChatColor.LIGHT_PURPLE + "n " + ChatColor.DARK_PURPLE + ":D");
					} else if (args[2].equalsIgnoreCase("off")) {
						plugin.getConfig().set("spleef.floor.rainbow", false);
						cs.sendMessage(ChatColor.GREEN + "Rainbow off. :(");
						plugin.saveConfig();
					} else {
						cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage floor rainbow <on/off>");
					}
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/spleefmanage world/timer/floor/pit/players");
			}
		}
		return true;
	}
}