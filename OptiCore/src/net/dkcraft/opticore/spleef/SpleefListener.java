package net.dkcraft.opticore.spleef;

import java.util.ArrayList;
import java.util.List;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class SpleefListener implements Listener {

	public Main plugin;
	private Methods methods;
	private NewSpleefRunnable spleefCountDown;
	//private Location location3;
	//private Inventory inventory3;

	public SpleefListener(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.spleefCountDown = this.plugin.spleefCountDown;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (methods.isInGame(player)) {
			player.sendMessage(ChatColor.RED + "You can't run commands while playing spleef.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final String playerName = player.getName();
		final PlayerInventory inventory = player.getInventory();

		World world = Bukkit.getWorld(plugin.getConfig().getString("spleef.world"));
		double corner1x = plugin.getConfig().getDouble("spleef.pit.corner1.x");
		double corner1y = plugin.getConfig().getDouble("spleef.pit.corner1.y");
		double corner1z = plugin.getConfig().getDouble("spleef.pit.corner1.z");
		double corner2x = plugin.getConfig().getDouble("spleef.pit.corner2.x");
		double corner2y = plugin.getConfig().getDouble("spleef.pit.corner2.y");
		double corner2z = plugin.getConfig().getDouble("spleef.pit.corner2.z");
		Location corner1 = new Location(world, corner1x, corner1y, corner1z);
		Location corner2 = new Location(world, corner2x, corner2y, corner2z);

		if (methods.isInGame(player)) {
			if (methods.pit(player.getLocation(), corner1, corner2) == true) {
				//if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
				//	return;
				//}

				Location location = plugin.spleefLocation.get(playerName);
				methods.preparePlayerFinish(player, location, inventory);
				methods.removePlayerFromGame(player);

				player.sendMessage(ChatColor.RED + "You've lost, bad luck!");
				methods.sendGamePlayerMessage(methods.playerFallMessage(player));
				
				// +1 to spleef_losses
				if (plugin.spleefLosses.containsKey(player.getName())) {
					plugin.spleefLosses.put(player.getName(), plugin.spleefLosses.get(player.getName()) + 1);
				} else {
					plugin.spleefLosses.put(player.getName(), 1);
				}

				if (methods.getGameSize() == 1) {
					List<String> toRemove = new ArrayList<String>();
					for (String spleefPlayers : plugin.spleefGame) {

						toRemove.add(spleefPlayers);

						@SuppressWarnings("deprecation")
						Player winner = Bukkit.getServer().getPlayer(spleefPlayers);

						// announce winner to server
						for (Player notification : Bukkit.getOnlinePlayers()) {
							if (!plugin.toggleNotifications.contains(notification.getName())) {
								if (!plugin.deafen.contains(notification.getName())) {
									notification.sendMessage(ChatColor.YELLOW + winner.getName() + ChatColor.GREEN + " has won spleef!");
								}
							}
						}

						Location location2 = plugin.spleefLocation.get(spleefPlayers);
						methods.preparePlayerFinish(winner, location2, inventory);
						
						// +1 to spleef_wins
						if (plugin.spleefWins.containsKey(winner.getName())) {
							plugin.spleefWins.put(winner.getName(), plugin.spleefWins.get(winner.getName()) + 1);
						} else {
							plugin.spleefWins.put(winner.getName(), 1);
						}

						methods.resetFloor();
					}

					methods.clearGame();
					toRemove.clear();

					if (methods.getQueueSize() == 2 || methods.getQueueSize() == 3) {

						for (String spleefPlayers : plugin.spleefQueue) {
							@SuppressWarnings("deprecation")
							Player players = Bukkit.getServer().getPlayer(spleefPlayers);

							players.sendMessage(ChatColor.GREEN + "Spleef game will start in " + methods.timeToStart() + " seconds.");
						}

						//start SpleefRunnable
						////new SpleefRunnable(this.plugin, location3, inventory3).runTaskLater(this.plugin, methods.timeToStart() * 20);
						spleefCountDown.startCountDown(methods.timeToStart() * 20);
						
					} else if (methods.getQueueSize() == 4) {
						
						//canel current runnable
						spleefCountDown.stopCountDown();
						
						//instantly start new runnable
						////new SpleefRunnable(this.plugin, location3, inventory3).run();
						spleefCountDown.startCountDown(0);
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String world = player.getWorld().getName();

		if (world.toLowerCase().equalsIgnoreCase(plugin.getConfig().getString("spleef.world"))) {
			if (!player.hasPermission("opticore.build.spleef")) {
				player.sendMessage(Syntax.BUILD_GENERAL);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		String world2 = player.getWorld().getName();
		Action action = event.getAction();
		Block block = event.getClickedBlock();

		World world = Bukkit.getWorld(plugin.getConfig().getString("spleef.world"));
		int floor1x = plugin.getConfig().getInt("spleef.floor.corner1.x");
		int floor1z = plugin.getConfig().getInt("spleef.floor.corner1.z");
		int floor2x = plugin.getConfig().getInt("spleef.floor.corner2.x");
		int floor2z = plugin.getConfig().getInt("spleef.floor.corner2.z");
		int floory = plugin.getConfig().getInt("spleef.floor.corner1.y");
		Location corner1 = new Location(world, floor1x, floory, floor1z);
		Location corner2 = new Location(world, floor2x, floory, floor2z);

		if (world2.toLowerCase().equalsIgnoreCase(plugin.getConfig().getString("spleef.world"))) {
			if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
				if (player.hasPermission("opticore.build.spleef") && !methods.isInGame(player)) {
					return;
				}
				if (!methods.isInGame(player)) {
					player.sendMessage(Syntax.BUILD_GENERAL);
					event.setCancelled(true);
				} else {
					if (block != null) {
						Location blocklocation = block.getLocation();
						if (methods.floor(blocklocation, corner1, corner2) == false) {
							event.setCancelled(true);
						} else {
							block.setType(Material.AIR);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		Block block = event.getBlock();

		if (plugin.spleefPitManage.containsKey(playerName)) {
			plugin.getConfig().set("spleef.pit." + plugin.spleefPitManage.get(playerName) + ".x", block.getX() + 0.5);
			plugin.getConfig().set("spleef.pit." + plugin.spleefPitManage.get(playerName) + ".y", block.getY());
			plugin.getConfig().set("spleef.pit." + plugin.spleefPitManage.get(playerName) + ".z", block.getZ() + 0.5);
			plugin.saveConfig();
			event.setCancelled(true);
			player.sendMessage(ChatColor.GREEN + "Location set for pit " + plugin.spleefPitManage.get(playerName) + ".");
			plugin.spleefPitManage.remove(playerName);
		}

		if (plugin.spleefFloorManage.containsKey(playerName)) {
			plugin.getConfig().set("spleef.floor." + plugin.spleefFloorManage.get(playerName) + ".x", block.getX());
			plugin.getConfig().set("spleef.floor." + plugin.spleefFloorManage.get(playerName) + ".y", block.getY());
			plugin.getConfig().set("spleef.floor." + plugin.spleefFloorManage.get(playerName) + ".z", block.getZ());
			plugin.saveConfig();
			event.setCancelled(true);
			player.sendMessage(ChatColor.GREEN + "Location set for floor " + plugin.spleefFloorManage.get(playerName) + ".");
			plugin.spleefFloorManage.remove(playerName);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Location location = plugin.spleefLocation.get(player.getName());
		Inventory inventory = player.getInventory();

		if (player.getGameMode().equals(GameMode.SURVIVAL)) {
			player.setGameMode(GameMode.CREATIVE);
			System.out.println("set " + player.getName() + "'s gamemode to creative");
		}

		if (plugin.spleefLocation.containsKey(player.getName())) {
			methods.restorePlayerLocation(player, location);
		}

		if (plugin.spleefInventory.containsKey(player.getName())) {
			methods.restorePlayerInventory(player, inventory);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		final PlayerInventory inventory = player.getInventory();
		
		if (methods.isInQueue(player)) {
			methods.removePlayerFromQueue(player);
		}

		if (methods.isInGame(player)) {
			methods.removePlayerFromGame(player);

			if (methods.getGameSize() == 1) {
				List<String> toRemove = new ArrayList<String>();
				for (String spleefPlayers : plugin.spleefGame) {

					toRemove.add(spleefPlayers);

					@SuppressWarnings("deprecation")
					Player winner = Bukkit.getServer().getPlayer(spleefPlayers);

					// announce winner to server
					for (Player notification : Bukkit.getOnlinePlayers()) {
						if (!plugin.toggleNotifications.contains(notification.getName())) {
							if (!plugin.deafen.contains(notification.getName())) {
								notification.sendMessage(ChatColor.YELLOW + winner.getName() + ChatColor.GREEN + " has won spleef!");
							}
						}
					}

					Location location2 = plugin.spleefLocation.get(spleefPlayers);
					methods.preparePlayerFinish(winner, location2, inventory);
					
					// +1 to spleef_wins
					if (plugin.spleefWins.containsKey(winner.getName())) {
						plugin.spleefWins.put(winner.getName(), plugin.spleefWins.get(winner.getName()) + 1);
					} else {
						plugin.spleefWins.put(winner.getName(), 1);
					}
					
					methods.resetFloor();
				}

				methods.clearGame();
				toRemove.clear();

				if (methods.getQueueSize() == 2 || methods.getQueueSize() == 3) {

					for (String spleefPlayers : plugin.spleefQueue) {
						@SuppressWarnings("deprecation")
						Player players = Bukkit.getServer().getPlayer(spleefPlayers);

						players.sendMessage(ChatColor.GREEN + "Spleef game will start in " + methods.timeToStart() + " seconds.");
					}

					//start SpleefRunnable
					////new SpleefRunnable(this.plugin, location3, inventory3).runTaskLater(this.plugin, methods.timeToStart() * 20);
					spleefCountDown.startCountDown(methods.timeToStart() * 20);

				} else if (methods.getQueueSize() == 4) {
					
					//canel current runnable
					spleefCountDown.stopCountDown();
					
					//instantly start new runnable
					////new SpleefRunnable(this.plugin, location3, inventory3).run();
					spleefCountDown.startCountDown(0);
				}
			}
		}
	}
}