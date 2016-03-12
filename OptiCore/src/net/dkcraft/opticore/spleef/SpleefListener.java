package net.dkcraft.opticore.spleef;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class SpleefListener implements Listener {

	public Main plugin;
	private SpleefMethods spleef;
	private SpleefRunnable spleefRunnable;

	public SpleefListener(Main plugin) {
		this.plugin = plugin;
		this.spleef = this.plugin.spleef;
		this.spleefRunnable = this.plugin.spleefRunnable;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (spleef.isInGame(player)) {
			player.sendMessage(ChatColor.RED + "You can't run commands while playing spleef.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final String playerName = player.getName();
		final PlayerInventory inventory = player.getInventory();
		Location playerLocation = player.getLocation();

		if (spleef.isInGame(player)) {
			if (spleef.pit(playerLocation) == true) {

				Location location = plugin.spleefLocation.get(playerName);
				spleef.preparePlayerFinish(player, location, inventory);
				spleef.removePlayerFromGame(player);

				player.sendMessage(ChatColor.RED + "You've lost, bad luck!");
				spleef.sendGamePlayerMessage(spleef.playerFallMessage(player));

				// +1 to spleef_losses
				if (plugin.spleefLosses.containsKey(player.getName())) {
					plugin.spleefLosses.put(player.getName(), plugin.spleefLosses.get(player.getName()) + 1);
				} else {
					plugin.spleefLosses.put(player.getName(), 1);
				}

				if (spleef.getGameSize() == 1) {
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
						
						spleef.launchFireworks();

						Location location2 = plugin.spleefLocation.get(spleefPlayers);
						spleef.preparePlayerFinish(winner, location2, inventory);

						// +1 to spleef_wins
						if (plugin.spleefWins.containsKey(winner.getName())) {
							plugin.spleefWins.put(winner.getName(), plugin.spleefWins.get(winner.getName()) + 1);
						} else {
							plugin.spleefWins.put(winner.getName(), 1);
						}

						spleef.resetFloor();
					}

					spleef.clearGame();
					toRemove.clear();

					if (spleef.getQueueSize() == 2 || spleef.getQueueSize() == 3) {

						for (String spleefPlayers : plugin.spleefQueue) {
							@SuppressWarnings("deprecation")
							Player players = Bukkit.getServer().getPlayer(spleefPlayers);

							players.sendMessage(ChatColor.GREEN + "Spleef game will start in " + spleef.timeToStart() + " seconds.");
						}

						//start SpleefRunnable
						spleefRunnable.startCountDown(spleef.timeToStart() * 20);

					} else if (spleef.getQueueSize() == 4) {

						//canel current runnable
						spleefRunnable.stopCountDown();

						//instantly start new runnable
						spleefRunnable.startCountDown(0);
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

		if (world2.toLowerCase().equalsIgnoreCase(plugin.getConfig().getString("spleef.world"))) {
			if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
				if (player.hasPermission("opticore.build.spleef") && !spleef.isInGame(player)) {
					return;
				}
				if (!spleef.isInGame(player)) {
					player.sendMessage(Syntax.BUILD_GENERAL);
					event.setCancelled(true);
				} else {
					if (block != null) {
						Location blockLocation = block.getLocation();
						if (spleef.floor(blockLocation) == false) {
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
			plugin.getConfig().set("spleef.pit." + plugin.spleefPitManage.get(playerName) + ".x", block.getX());
			plugin.getConfig().set("spleef.pit." + plugin.spleefPitManage.get(playerName) + ".y", block.getY());
			plugin.getConfig().set("spleef.pit." + plugin.spleefPitManage.get(playerName) + ".z", block.getZ());
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
			spleef.restorePlayerLocation(player, location);
		}

		if (plugin.spleefInventory.containsKey(player.getName())) {
			spleef.restorePlayerInventory(player, inventory);
		}

		if (plugin.spleefArmor.containsKey(player.getName())) {
			spleef.restorePlayerArmor(player, inventory);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		final PlayerInventory inventory = player.getInventory();

		if (spleef.isInQueue(player)) {
			spleef.removePlayerFromQueue(player);
		}

		if (spleef.isInGame(player)) {
			spleef.removePlayerFromGame(player);

			if (spleef.getGameSize() == 1) {
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
					
					spleef.launchFireworks();

					Location location2 = plugin.spleefLocation.get(spleefPlayers);
					spleef.preparePlayerFinish(winner, location2, inventory);

					// +1 to spleef_wins
					if (plugin.spleefWins.containsKey(winner.getName())) {
						plugin.spleefWins.put(winner.getName(), plugin.spleefWins.get(winner.getName()) + 1);
					} else {
						plugin.spleefWins.put(winner.getName(), 1);
					}

					spleef.resetFloor();
				}

				spleef.clearGame();
				toRemove.clear();

				if (spleef.getQueueSize() == 2 || spleef.getQueueSize() == 3) {

					for (String spleefPlayers : plugin.spleefQueue) {
						@SuppressWarnings("deprecation")
						Player players = Bukkit.getServer().getPlayer(spleefPlayers);

						players.sendMessage(ChatColor.GREEN + "Spleef game will start in " + ChatColor.YELLOW + spleef.timeToStart() + ChatColor.GREEN + " seconds.");
					}

					//start SpleefRunnable
					spleefRunnable.startCountDown(spleef.timeToStart() * 20);

				} else if (spleef.getQueueSize() == 4) {

					//canel current runnable
					spleefRunnable.stopCountDown();

					//instantly start new runnable
					spleefRunnable.startCountDown(0);
				}
			}
		}
	}
}
