package net.dkcraft.opticore.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.InventoryHolder;

import net.dkcraft.opticore.Main;

public class ItemDropListener implements Listener {

	public Main plugin;

	public ItemDropListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material blockType = block.getType();
		if (blockType.equals(Material.BREWING_STAND)) {
			BrewingStand brewingstand = (BrewingStand) block.getState();
			brewingstand.getInventory().clear();
		}

		if (blockType.equals(Material.CHEST)) {
			Chest chest = (Chest) block.getState();
			chest.getInventory().clear();
		}

		if (blockType.equals(Material.DISPENSER)) {
			Dispenser dispenser = (Dispenser) block.getState();
			dispenser.getInventory().clear();
		}

		if (blockType.equals(Material.DROPPER)) {
			Dropper dropper = (Dropper) block.getState();
			dropper.getInventory().clear();
		}

		if (blockType.equals(Material.FURNACE)) {
			Furnace furnace = (Furnace) block.getState();
			furnace.getInventory().clear();
		}

		if (blockType.equals(Material.HOPPER)) {
			Hopper hopper = (Hopper) block.getState();
			hopper.getInventory().clear();
		}

		if (blockType.equals(Material.JUKEBOX)) {
			Jukebox jukebox = (Jukebox) block.getState();
			if (jukebox.isPlaying()) {
				jukebox.setPlaying(Material.AIR);
			}
		}

		if (blockType.equals(Material.TRAPPED_CHEST)) {
			Chest chest = (Chest) block.getState();
			chest.getInventory().clear();
		}
	}

	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		Vehicle vehicle = event.getVehicle();
		EntityType vehicleType = vehicle.getType();
		if (vehicleType.equals(EntityType.MINECART_CHEST)) {
			((InventoryHolder) vehicle).getInventory().clear();
		}

		if (vehicleType.equals(EntityType.MINECART_HOPPER)) {
			((InventoryHolder) vehicle).getInventory().clear();
		}
	}
}
