package net.dkcraft.opticore.label;

import java.util.Set;

import net.dkcraft.opticore.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LabelMethods {

	public Main plugin;

	//private ChatColor RED = ChatColor.RED;
	private ChatColor YELLOW = ChatColor.YELLOW;
	private ChatColor GREEN = ChatColor.GREEN;

	public LabelMethods(Main plugin) {
		this.plugin = plugin;
	}

	// Create label
	public void createLabel(Player player, String name, String text) {

		World world = player.getLocation().getWorld();
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();

		Location location = new Location(world, x, y, z);

		plugin.getConfig().set("labels." + player.getName().toLowerCase() + "." + name, "");
		plugin.getConfig().set("labels." + player.getName().toLowerCase() + "." + name + ".location", "");
		plugin.getConfig().set("labels." + player.getName().toLowerCase() + "." + name + ".location.world", world.getName());
		plugin.getConfig().set("labels." + player.getName().toLowerCase() + "." + name + ".location.x", x);
		plugin.getConfig().set("labels." + player.getName().toLowerCase() + "." + name + ".location.y", y);
		plugin.getConfig().set("labels." + player.getName().toLowerCase() + "." + name + ".location.z", z);
		plugin.getConfig().set("labels." + player.getName().toLowerCase() + "." + name + ".text", text);
		plugin.saveConfig();

		createFirstLine(player, location, text);
		createSecondLine(player, location);
	}

	// Creates first armour stand line
	public void createFirstLine(Player player, Location location, String text) {

		World world = player.getLocation().getWorld();

		ArmorStand stand = (ArmorStand) world.spawn(location.add(0, -0.7, 0), ArmorStand.class);
		stand.setVisible(false);
		stand.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
		stand.setCustomNameVisible(true);
		stand.setGravity(false);
		stand.setBasePlate(false);
	}

	// Creates second armour stand line
	public void createSecondLine(Player player, Location location) {

		World world = player.getLocation().getWorld();

		ArmorStand stand = (ArmorStand) world.spawn(location.add(0, -0.3, 0), ArmorStand.class);
		stand.setVisible(false);
		stand.setCustomName("by: " + player.getName());
		stand.setCustomNameVisible(true);
		stand.setGravity(false);
		stand.setBasePlate(false);
	}

	// Creates armour stand to delete
	public void createDeleteStand(World world, Location location) {

		ArmorStand stand = (ArmorStand) world.spawn(location.add(0, -0.7, 0), ArmorStand.class);
		stand.setVisible(false);
		stand.setCustomNameVisible(false);
		stand.setGravity(false);
		stand.setBasePlate(false);

		for (Entity entity : stand.getNearbyEntities(1, 1, 1)) {
			if (entity.getType().equals(EntityType.ARMOR_STAND)) {
				entity.remove();
			}
		}

		stand.remove();
	}

	// Check if player label exists
	public boolean playerLabelExists(Player player, String name) {
		return plugin.getConfig().contains("labels." + player.getName().toLowerCase() + "." + name);
	}

	// Check if owner label exists
	public boolean ownerLabelExists(String owner, String name) {
		return plugin.getConfig().contains("labels." + owner.toLowerCase() + "." + name);
	}

	// Check if owner has label
	public boolean ownerHasLabels(String owner) {
		return plugin.getConfig().contains("labels." + owner.toLowerCase());
	}

	// Check if chunk is loaded
	public void loadChunk(Location location) {
		Chunk chunk = location.getChunk();
		if (!chunk.isLoaded()) {
			chunk.load();
		}
	}

	// Delete label
	public void deleteLabel(String owner, String name) {

		World world = Bukkit.getWorld(plugin.getConfig().getString("labels." + owner.toLowerCase() + "." + name + ".location.world"));
		double x = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.x");
		double y = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.y");
		double z = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.z");
		Location location = new Location(world, x, y, z);

		// to-do: fix check to see if chunk is loaded
		loadChunk(location);

		createDeleteStand(world, location);

		plugin.getConfig().set("labels." + owner.toLowerCase() + "." + name, null);
		plugin.saveConfig();
	}

	// Delete nearby armour stands
	public void deleteNear(Player player) {

		for (Entity entity : player.getNearbyEntities(2, 2, 2)) {
			if (entity.getType().equals(EntityType.ARMOR_STAND)) {
				if (entity.getCustomName() != null) {
					entity.remove();
				}
			}
		}
	}

	// Edit label
	public void editLabel(Player player, String owner, String name, String text) {

		World world = Bukkit.getWorld(plugin.getConfig().getString("labels." + owner.toLowerCase() + "." + name + ".location.world"));
		double x = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.x");
		double y = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.y");
		double z = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.z");
		Location location = new Location(world, x, y, z);

		// to-do: fix check to see if chunk is loaded
		loadChunk(location);

		createDeleteStand(world, location);

		createFirstLine(player, location.add(0, 0.7, 0), text);
		createSecondLine(player, location);

		plugin.getConfig().set("labels." + owner.toLowerCase() + "." + name + ".text", text);
		plugin.saveConfig();
	}

	// Move label
	public void moveLabel(Player player, String owner, String name) {

		World world = Bukkit.getWorld(plugin.getConfig().getString("labels." + owner.toLowerCase() + "." + name + ".location.world"));
		double x = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.x");
		double y = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.y");
		double z = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.z");
		String text = plugin.getConfig().getString("labels." + owner.toLowerCase() + "." + name + ".text");
		Location location = new Location(world, x, y, z);

		// to-do: fix check to see if chunk is loaded
		loadChunk(location);

		createDeleteStand(world, location);

		World world2 = player.getLocation().getWorld();
		double x2 = player.getLocation().getX();
		double y2 = player.getLocation().getY();
		double z2 = player.getLocation().getZ();

		Location location2 = new Location(world2, x2, y2, z2);

		createFirstLine(player, location2, text);
		createSecondLine(player, location2);

		plugin.getConfig().set("labels." + owner.toLowerCase() + "." + name + ".location.world", world2.getName());
		plugin.getConfig().set("labels." + owner.toLowerCase() + "." + name + ".location.x", x2);
		plugin.getConfig().set("labels." + owner.toLowerCase() + "." + name + ".location.y", y2);
		plugin.getConfig().set("labels." + owner.toLowerCase() + "." + name + ".location.z", z2);
		plugin.saveConfig();
	}

	// Teleport to label
	public void teleportLabel(Player player, String owner, String name) {

		World world = Bukkit.getWorld(plugin.getConfig().getString("labels." + owner.toLowerCase() + "." + name + ".location.world"));
		double x = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.x");
		double y = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.y");
		double z = plugin.getConfig().getDouble("labels." + owner.toLowerCase() + "." + name + ".location.z");
		Location location = new Location(world, x, y, z);

		player.teleport(location);
	}

	// List player labels
	public void listPlayerLabels(Player player, String owner) {

		Set<String> list = plugin.getConfig().getConfigurationSection("labels." + owner.toLowerCase()).getKeys(false);
		int size = list.size();

		player.sendMessage(GREEN + owner + " has " + YELLOW + size + GREEN + " labels.");

		for (String list2 : list) {

			World world = Bukkit.getWorld(plugin.getConfig().getString("labels." + owner.toLowerCase() + "." + list2 + ".location.world"));
			int x = plugin.getConfig().getInt("labels." + owner.toLowerCase() + "." + list2 + ".location.x");
			int y = plugin.getConfig().getInt("labels." + owner.toLowerCase() + "." + list2 + ".location.y");
			int z = plugin.getConfig().getInt("labels." + owner.toLowerCase() + "." + list2 + ".location.z");

			String text = plugin.getConfig().getString("labels." + owner.toLowerCase() + "." + list2 + ".text");

			player.sendMessage(GREEN + "Name: " + YELLOW + list2);
			player.sendMessage(GREEN + " Location: " + YELLOW + world.getName() + ", " + x + ", " + y + ", " + z);
			player.sendMessage(GREEN + " Text: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', text));
		}
	}

	public boolean labelsIsEmpty() {
		return plugin.getConfig().getConfigurationSection("labels").getKeys(false).isEmpty();
	}

	// List world labels - this is extremely broken and needs to be-done
	public void listWorldLabels(Player player, String world) {

		for (String owner : plugin.getConfig().getConfigurationSection("labels").getKeys(false)) {
			for (String name : plugin.getConfig().getConfigurationSection("labels." + owner).getKeys(false)) {

				String world2 = plugin.getConfig().getString("labels." + owner + "." + name + ".location.world");

				//player.sendMessage(GREEN + world2 + " has " + YELLOW + size + GREEN + " labels.");

				if (world2.equalsIgnoreCase(world)) {
					//player.sendMessage(GREEN + owner + name + world2);

					int x = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.x");
					int y = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.y");
					int z = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.z");
					String text = plugin.getConfig().getString("labels." + owner + "." + name + ".text");

					player.sendMessage(GREEN + "Owner: " + YELLOW + owner);
					player.sendMessage(GREEN + " Name: " + YELLOW + name);
					player.sendMessage(GREEN + " Location: " + YELLOW + world2 + ", " + x + ", " + y + ", " + z);
					player.sendMessage(GREEN + " Text: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', text));
				}

				//world = plugin.getConfig().getString("labels." + owner + "." + name + ".location.world");
				//Set<String> list = plugin.getConfig().getConfigurationSection("labels." + owner + "." + name + ".location.world").getKeys(false);
				//int size = list.size();

				/*
				for (String list2 : list) {
					int x = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.x");
					int y = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.y");
					int z = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.z");
					String text = plugin.getConfig().getString("labels." + owner + "." + name + ".text");

					player.sendMessage(GREEN + "Owner: " + YELLOW + owner);
					player.sendMessage(GREEN + " Name: " + YELLOW + name);
					player.sendMessage(GREEN + " Location: " + YELLOW + world + ", " + x + ", " + y + ", " + z);
					player.sendMessage(GREEN + " Text: " + YELLOW + text);
				}
				 */
			}
		}
	}

	// List all labels
	public void listAllLabels(Player player) {

		for (String owner : plugin.getConfig().getConfigurationSection("labels").getKeys(false)) {

			Set<String> list = plugin.getConfig().getConfigurationSection("labels." + owner).getKeys(false);
			int size = list.size();

			player.sendMessage(GREEN + "There are " + YELLOW + size + GREEN + " total labels.");

			for (String name : plugin.getConfig().getConfigurationSection("labels." + owner).getKeys(false)) {

				String world = plugin.getConfig().getString("labels." + owner + "." + name + ".location.world");
				int x = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.x");
				int y = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.y");
				int z = plugin.getConfig().getInt("labels." + owner + "." + name + ".location.z");
				String text = plugin.getConfig().getString("labels." + owner + "." + name + ".text");

				player.sendMessage(GREEN + "Owner: " + YELLOW + owner);
				player.sendMessage(GREEN + " Name: " + YELLOW + name);
				player.sendMessage(GREEN + " Location: " + YELLOW + world + ", " + x + ", " + y + ", " + z);
				player.sendMessage(GREEN + " Text: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', text));
			}
		}
	}

	// Purge a players labels
	public void purgePlayerLabels(Player player, String owner) {
		// to-do
	}

	// Purge a players labels
	public void purgeWorldLabels(Player player, String owner) {
		// to-do
	}

	// Purge a players labels
	public void purgeAllLabels(Player player, String owner) {
		// to-do
	}

	public void sendHelpMenu(Player player) {

		player.sendMessage(YELLOW + "Available Label Commands:");

		if (player.hasPermission("opticore.label.create")) {
			player.sendMessage(GREEN + " /label create <name> <text>");
		}
		if (player.hasPermission("opticore.label.delete")) {
			player.sendMessage(GREEN + " /label delete <player> <name>");
		}
		if (player.hasPermission("opticore.label.edit")) {
			player.sendMessage(GREEN + " /label edit <player> <name> <text>");
		}
		if (player.hasPermission("opticore.label.move")) {
			player.sendMessage(GREEN + " /label move <player> <name>");
		}
		if (player.hasPermission("opticore.label.teleport")) {
			player.sendMessage(GREEN + " /label teleport <player> <name>");
		}
		if (player.hasPermission("opticore.label.list.player")) {
			player.sendMessage(GREEN + " /label list player <player>");
		}
		if (player.hasPermission("opticore.label.list.world")) {
			player.sendMessage(GREEN + " /label list world <world> [time]");
		}
		if (player.hasPermission("opticore.label.list.all")) {
			player.sendMessage(GREEN + " /label list all");
		}
		if (player.hasPermission("opticore.label.purge.player")) {
			player.sendMessage(GREEN + " /label purge player <player>");
		}
		if (player.hasPermission("opticore.label.purge.world")) {
			player.sendMessage(GREEN + " /label purge world <world>");
		}
		if (player.hasPermission("opticore.label.purge.all")) {
			player.sendMessage(GREEN + " /label purge all");
		}
	}
}