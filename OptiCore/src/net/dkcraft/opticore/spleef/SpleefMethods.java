package net.dkcraft.opticore.spleef;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;

import com.earth2me.essentials.Essentials;

import net.dkcraft.opticore.Main;

public class SpleefMethods {

	public Main plugin;

	Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	private ChatColor YELLOW = ChatColor.YELLOW;
	private ChatColor GREEN = ChatColor.GREEN;

	public SpleefMethods(Main plugin) {
		this.plugin = plugin;
	}

	// Prepare player start
	public void preparePlayerStart(Player player, Location location, Inventory inventory) {
		plugin.spleefLocation.put(player.getName(), location);
		//System.out.println("stored " + player.getName() + "'s location");

		plugin.spleefInventory.put(player.getName(), inventory.getContents());
		//System.out.println("stored " + player.getName() + "'s inventory");

		player.getInventory().clear();
		//System.out.println("cleared " + player.getName() + "'s inventory");

		plugin.spleefArmor.put(player.getName(), player.getInventory().getArmorContents());
		//System.out.println("stored " + player.getName() + "'s armor");

		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		//System.out.println("cleared " + player.getName() + "'s armor");

		player.setGameMode(GameMode.SURVIVAL);
		//System.out.println("set " + player.getName() + "'s gamemode to survival");

		player.setHealth(20);
		//System.out.println("set " + player.getName() + "'s health");
		player.setFoodLevel(20);
		//System.out.println("set " + player.getName() + "'s hunger");

		player.setWalkSpeed(0.2F);
		//System.out.println("set " + player.getName() + "'s walk speed");

		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		//System.out.println("cleared " + player.getName() + "'s potions");

		if (essentials != null) {
			if (essentials.getUser(player).isVanished()) {
				essentials.getUser(player).setVanished(false);
			}
		}
		//System.out.println("unvanished " + player.getName() + " via essentials");

		plugin.spleefGame.add(player.getName());
		//System.out.println("added " + player.getName() + " to spleef game");
	}

	// Prepare player finish
	public void preparePlayerFinish(Player player, Location location, Inventory inventory) {

		restorePlayerLocation(player, location);
		restorePlayerInventory(player, inventory);
		restorePlayerArmor(player, inventory);

		player.setGameMode(GameMode.CREATIVE);
		//System.out.println("set " + player.getName() + "'s gamemode to creative");

		player.setFlying(true);
	}

	// Add player to queue
	public void addPlayerToQueue(Player player) {
		plugin.spleefQueue.add(player.getName());
		player.sendMessage(playerQueueMessage());
		//System.out.println("added " + player.getName() + " to spleef queue");
	}

	// Send queue players a message
	public void sendQueuePlayerMessage(String message) {
		for (String spleefPlayers : plugin.spleefQueue) {
			@SuppressWarnings("deprecation")
			Player players = Bukkit.getServer().getPlayer(spleefPlayers);

			players.sendMessage(message);
			// players.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has joined the spleef queue.");
		}
	}

	// Player join queue message
	public String playerJoinQueueMessage(Player player) {
		return YELLOW + player.getName() + GREEN + " has joined the spleef queue.";
	}

	// Send game players a message
	public void sendGamePlayerMessage(String message) {
		for (String spleefPlayers : plugin.spleefGame) {
			@SuppressWarnings("deprecation")
			Player players = Bukkit.getServer().getPlayer(spleefPlayers);

			players.sendMessage(message);
			// players.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has joined the spleef queue.");
		}
	}

	// Player fall in pit message
	public String playerFallMessage(Player player) {
		return YELLOW + player.getName() + GREEN + " has fallen in the pit.";
	}

	// Remove players from queue
	public void clearQueue() {
		plugin.spleefQueue.clear();
	}

	// Remove players from game
	public void clearGame() {
		plugin.spleefGame.clear();
	}

	// Remove player from game
	public void removePlayerFromGame(Player player) {
		plugin.spleefGame.remove(player.getName());
		//System.out.println("removed " + player.getName() + " from spleef game");
	}

	// Remove player from queue
	public void removePlayerFromQueue(Player player) {
		plugin.spleefQueue.remove(player.getName());
		//System.out.println("removed " + player.getName() + " from spleef queue");
	}

	// Restore player location
	public void restorePlayerLocation(Player player, Location location) {
		player.teleport(location);
		plugin.spleefLocation.remove(player.getName());
		//System.out.println("restored " + player.getName() + "'s location");
	}

	// Restore player inventory
	public void restorePlayerInventory(Player player, Inventory inventory) {
		player.getInventory().setContents(plugin.spleefInventory.get(player.getName()));
		plugin.spleefInventory.remove(player.getName());
		//System.out.println("restored " + player.getName() + "'s inventory");
	}

	// Restore player armor
	public void restorePlayerArmor(Player player, Inventory inventory) {
		player.getInventory().setArmorContents(plugin.spleefArmor.get(player.getName()));
		plugin.spleefArmor.remove(player.getName());
		//System.out.println("restored " + player.getName() + "'s armor");
	}

	// Get player location
	public void getPlayerLocation(Player player) {
		player.getLocation();
	}

	// Check player in queue
	public boolean isInQueue(Player player) {
		return plugin.spleefQueue.contains(player.getName());
	}

	// Check player in game
	public boolean isInGame(Player player) {
		return plugin.spleefGame.contains(player.getName());
	}

	// Get queue size
	public int getQueueSize() {
		return plugin.spleefQueue.size();
	}

	// Get game size
	public int getGameSize() {
		return plugin.spleefGame.size();
	}

	// Send queue message
	public String playerQueueMessage() {
		return GREEN + "You have been added to the spleef queue.\n" +
				GREEN + "Queue size: " + YELLOW + getQueueSize() + "/4";
	}

	// Get time to start
	public int timeToStart() {
		return plugin.getConfig().getInt("spleef.timer");
	}

	// Launch fireworks
	public void launchFireworks() {

		World world = Bukkit.getWorld(plugin.getConfig().getString("spleef.world"));

		int firework1X = plugin.getConfig().getInt("spleef.fireworks.firework1.x");
		int firework1Y = plugin.getConfig().getInt("spleef.fireworks.firework1.y");
		int firework1Z = plugin.getConfig().getInt("spleef.fireworks.firework1.z");
		Location firework1Location = new Location(world, firework1X, firework1Y, firework1Z);
		
		int firework2X = plugin.getConfig().getInt("spleef.fireworks.firework2.x");
		int firework2Y = plugin.getConfig().getInt("spleef.fireworks.firework2.y");
		int firework2Z = plugin.getConfig().getInt("spleef.fireworks.firework2.z");
		Location firework2Location = new Location(world, firework2X, firework2Y, firework2Z);
		
		int firework3X = plugin.getConfig().getInt("spleef.fireworks.firework3.x");
		int firework3Y = plugin.getConfig().getInt("spleef.fireworks.firework3.y");
		int firework3Z = plugin.getConfig().getInt("spleef.fireworks.firework3.z");
		Location firework3Location = new Location(world, firework3X, firework3Y, firework3Z);
		
		int firework4X = plugin.getConfig().getInt("spleef.fireworks.firework4.x");
		int firework4Y = plugin.getConfig().getInt("spleef.fireworks.firework4.y");
		int firework4Z = plugin.getConfig().getInt("spleef.fireworks.firework4.z");
		Location firework4Location = new Location(world, firework4X, firework4Y, firework4Z);

		Firework firework1 = (Firework) world.spawn(firework1Location, Firework.class);
		Firework firework2 = (Firework) world.spawn(firework2Location, Firework.class);
		Firework firework3 = (Firework) world.spawn(firework3Location, Firework.class);
		Firework firework4 = (Firework) world.spawn(firework4Location, Firework.class);

		FireworkMeta meta = firework1.getFireworkMeta();

		Random r = new Random();

		int fType = r.nextInt(5) + 1;

		Type type = null;

		switch (fType) {
		default:
		case 1:
			type = Type.BALL;
			break;
		case 2:
			type = Type.BALL_LARGE;
			break;
		case 3:
			type = Type.BURST;
			break;
		case 4:
			type = Type.CREEPER;
			break;
		case 5:
			type = Type.STAR;
			break;
		}

		int c1i = r.nextInt(17) + 1;
		int c2i = r.nextInt(17) + 1;

		Color c1 = getColour(c1i);
		Color c2 = getColour(c2i);

		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
		
		meta.addEffect(effect);
		
		int power = r.nextInt(2) + 1;
		
		meta.setPower(power);
		
		firework1.setFireworkMeta(meta);
		firework2.setFireworkMeta(meta);
		firework3.setFireworkMeta(meta);
		firework4.setFireworkMeta(meta);
	}

	public Color getColour(int colour) {

		switch (colour) {
		default:
		case 1:
			return Color.AQUA;
		case 2:
			return Color.BLACK;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.FUCHSIA;
		case 5:
			return Color.GRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.LIME;
		case 8:
			return Color.MAROON;
		case 9:
			return Color.NAVY;
		case 10:
			return Color.OLIVE;
		case 11:
			return Color.ORANGE;
		case 12:
			return Color.PURPLE;
		case 13:
			return Color.RED;
		case 14:
			return Color.SILVER;
		case 15:
			return Color.TEAL;
		case 16:
			return Color.WHITE;
		case 17:
			return Color.YELLOW;
		}
	}

	// Floor area
	public boolean floor(Location location) {

		int xCorner1 = plugin.getConfig().getInt("spleef.floor.corner1.x");
		int xCorner2 = plugin.getConfig().getInt("spleef.floor.corner2.x");
		int x1 = Math.min(xCorner1, xCorner2);
		int x2 = Math.max(xCorner1, xCorner2);

		int yCorner1 = plugin.getConfig().getInt("spleef.floor.corner1.y");
		int yCorner2 = plugin.getConfig().getInt("spleef.floor.corner2.y");
		int y1 = Math.min(yCorner1, yCorner2);
		int y2 = Math.max(yCorner1, yCorner2);

		int zCorner1 = plugin.getConfig().getInt("spleef.floor.corner1.z");
		int zCorner2 = plugin.getConfig().getInt("spleef.floor.corner2.z");
		int z1 = Math.min(zCorner1, zCorner2);
		int z2 = Math.max(zCorner1, zCorner2);

		return location.getX() >= x1 && location.getX() <= x2 && location.getY() >= y1 && location.getY() <= y2 && location.getZ() >= z1 && location.getZ() <= z2;
	}

	// Pit area
	public boolean pit(Location location) {

		int xCorner1 = plugin.getConfig().getInt("spleef.pit.corner1.x");
		int xCorner2 = plugin.getConfig().getInt("spleef.pit.corner2.x");
		int x1 = Math.min(xCorner1, xCorner2);
		int x2 = Math.max(xCorner1, xCorner2) +1;

		int yCorner1 = plugin.getConfig().getInt("spleef.pit.corner1.y");
		int yCorner2 = plugin.getConfig().getInt("spleef.pit.corner2.y");
		int y1 = Math.min(yCorner1, yCorner2);
		int y2 = Math.max(yCorner1, yCorner2);

		int zCorner1 = plugin.getConfig().getInt("spleef.pit.corner1.z");
		int zCorner2 = plugin.getConfig().getInt("spleef.pit.corner2.z");
		int z1 = Math.min(zCorner1, zCorner2);
		int z2 = Math.max(zCorner1, zCorner2) +1;

		return location.getX() >= x1 && location.getX() <= x2 && location.getY() >= y1 && location.getY() <= y2 && location.getZ() >= z1 && location.getZ() <= z2;
	}

	// Reset floor
	@SuppressWarnings("deprecation")
	public void resetFloor() {

		World world = Bukkit.getWorld(plugin.getConfig().getString("spleef.world"));

		int xCorner1 = plugin.getConfig().getInt("spleef.floor.corner1.x");
		int xCorner2 = plugin.getConfig().getInt("spleef.floor.corner2.x");
		int x1 = Math.min(xCorner1, xCorner2);
		int x2 = Math.max(xCorner1, xCorner2);

		int yCorner1 = plugin.getConfig().getInt("spleef.floor.corner1.y");
		int yCorner2 = plugin.getConfig().getInt("spleef.floor.corner2.y");
		int y1 = Math.min(yCorner1, yCorner2);
		int y2 = Math.max(yCorner1, yCorner2);

		int zCorner1 = plugin.getConfig().getInt("spleef.floor.corner1.z");
		int zCorner2 = plugin.getConfig().getInt("spleef.floor.corner2.z");
		int z1 = Math.min(zCorner1, zCorner2);
		int z2 = Math.max(zCorner1, zCorner2);

		int blockType = plugin.getConfig().getInt("spleef.floor.blocktype");
		int blockData = plugin.getConfig().getInt("spleef.floor.blockdata");


		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				for (int z = z1; z <= z2; z++) {
					if (plugin.getConfig().getBoolean("spleef.floor.rainbow") == true) {

						Random object = new Random();
						int num;

						Block block = world.getBlockAt(x, y, z);
						block.setType(Material.WOOL);
						for (int counter =1; counter <=1; counter++) {
							num = 1 + object.nextInt(15);
							block.setData((byte)num);
						}
					} else {
						world.getBlockAt(x, y, z).setTypeIdAndData(blockType, (byte) blockData, true);
					}
				}
			}
		}
	}

}