package net.dkcraft.opticore.commands;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.InventoryGUI;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Classic implements CommandExecutor {

	private Main plugin;

	public InventoryGUI inventorygui = new InventoryGUI(plugin);
	
	public Classic(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("classic")) {
			if (args.length == 0) {
				Player player = (Player) cs;
				String playerName = player.getName();
				if (!plugin.classic.contains(playerName)) {
					plugin.classic.add(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled classic mode on. Right click to open inventory.");
					player.getInventory().clear();
					ItemStack[] items = {
							new ItemStack(org.bukkit.Material.STONE),
							new ItemStack(org.bukkit.Material.COBBLESTONE),
							new ItemStack(org.bukkit.Material.BRICK),
							new ItemStack(org.bukkit.Material.DIRT),
							new ItemStack(org.bukkit.Material.WOOD),
							new ItemStack(org.bukkit.Material.LOG),
							new ItemStack(org.bukkit.Material.LEAVES),
							new ItemStack(org.bukkit.Material.GLASS),
							new ItemStack(org.bukkit.Material.STEP)};
					player.getInventory().addItem(items);
				} else {
					plugin.classic.remove(playerName);
					player.sendMessage(ChatColor.GREEN + "Toggled classic mode off.");
					player.getInventory().clear();
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/classic " + ChatColor.GRAY + ChatColor.ITALIC + "don't tell tobs");
			}
		}
		return true;
	}
}