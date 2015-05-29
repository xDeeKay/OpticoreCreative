package net.dkcraft.opticore.listeners;

import net.dkcraft.opticore.Main;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;

public class PaintListener implements Listener {

	public Main plugin;

	private Consumer consumer;

	public PaintListener(Main plugin, LogBlock logblock) {
		this.plugin = plugin;
		consumer = logblock.getConsumer();
	}

	LogBlock logblock = (LogBlock) Bukkit.getServer().getPluginManager().getPlugin("LogBlock");

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		ItemStack hand = player.getItemInHand();
		Block block = event.getBlock();
		if (plugin.paint.contains(playerName)) {
			if (hand != null) {
				if (hand.getType().isBlock()) {
					if (hand.getData().getData() != block.getData()) {
						event.setCancelled(true);
						consumer.queueBlockReplace(playerName, block.getState(), hand.getTypeId(), hand.getData().getData());
						block.setTypeIdAndData(hand.getTypeId(), hand.getData().getData(), true);
					}
				}
			}
		}
	}
}