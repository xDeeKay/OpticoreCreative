package net.dkcraft.opticore.spleef;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.dkcraft.opticore.Main;

public class NewSpleefRunnable {
	
	public Main plugin;
	private Methods methods;
	private BukkitTask task;

	public NewSpleefRunnable(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
	}
	
	public void startCountDown(int timeToStart) {
		this.task = new BukkitRunnable() {
			@Override
			public void run() {
				//teleport players to arena
				World world = Bukkit.getWorld(plugin.getConfig().getString("spleef.world"));

				//player 1 location
				double player1x = plugin.getConfig().getDouble("spleef.players.player1.x");
				double player1y = plugin.getConfig().getDouble("spleef.players.player1.y");
				double player1z = plugin.getConfig().getDouble("spleef.players.player1.z");
				float player1yaw = (float) plugin.getConfig().getDouble("spleef.players.player1.yaw");
				float player1pitch = (float) plugin.getConfig().getDouble("spleef.players.player1.pitch");
				Location player1loc = new Location(world, player1x, player1y, player1z, player1yaw, player1pitch);

				//player 2 location
				double player2x = plugin.getConfig().getDouble("spleef.players.player2.x");
				double player2y = plugin.getConfig().getDouble("spleef.players.player2.y");
				double player2z = plugin.getConfig().getDouble("spleef.players.player2.z");
				float player2yaw = (float) plugin.getConfig().getDouble("spleef.players.player2.yaw");
				float player2pitch = (float) plugin.getConfig().getDouble("spleef.players.player2.pitch");
				Location player2loc = new Location(world, player2x, player2y, player2z, player2yaw, player2pitch);

				//player 3 location
				double player3x = plugin.getConfig().getDouble("spleef.players.player3.x");
				double player3y = plugin.getConfig().getDouble("spleef.players.player3.y");
				double player3z = plugin.getConfig().getDouble("spleef.players.player3.z");
				float player3yaw = (float) plugin.getConfig().getDouble("spleef.players.player3.yaw");
				float player3pitch = (float) plugin.getConfig().getDouble("spleef.players.player3.pitch");
				Location player3loc = new Location(world, player3x, player3y, player3z, player3yaw, player3pitch);

				//player 4 location
				double player4x = plugin.getConfig().getDouble("spleef.players.player4.x");
				double player4y = plugin.getConfig().getDouble("spleef.players.player4.y");
				double player4z = plugin.getConfig().getDouble("spleef.players.player4.z");
				float player4yaw = (float) plugin.getConfig().getDouble("spleef.players.player4.yaw");
				float player4pitch = (float) plugin.getConfig().getDouble("spleef.players.player4.pitch");
				Location player4loc = new Location(world, player4x, player4y, player4z, player4yaw, player4pitch);

				Location[] locations = new Location[4];
				locations[0] = player1loc;
				locations[1] = player2loc;
				locations[2] = player3loc;
				locations[3] = player4loc;

				List<String> toRemove = new ArrayList<String>();

				int i = 0;
				for (String spleefPlayers : plugin.spleefQueue) {

					toRemove.add(spleefPlayers);

					@SuppressWarnings("deprecation")
					Player players = Bukkit.getServer().getPlayer(spleefPlayers);

					Location location = players.getLocation();
					Inventory inventory = players.getInventory();

					players.sendMessage(ChatColor.GREEN + "Spleef!");

					methods.preparePlayerStart(players, location, inventory);

					players.teleport(locations[i]);

					//System.out.println("Teleported " + player2.getName() + " to the arena");
					//System.out.println("Queue size before: " + methods.getQueueSize());
					//System.out.println("toRemove size before: " + toRemove.size());

					i++;
				}

				methods.resetFloor();

				methods.clearQueue();
				toRemove.clear();
				//System.out.println("Queue size after: " + methods.getQueueSize());
				//System.out.println("toRemove size after: " + toRemove.size());
			}
		}.runTaskLater(plugin, timeToStart);
	}
	
	public void startInstantly() {
		
	}

	public void stopCountDown() {
		if (this.task != null) {
			this.task.cancel();
			this.task = null;
		}
	}
}