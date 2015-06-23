package net.dkcraft.opticore.util;


import net.dkcraft.opticore.Main;

import org.bukkit.event.Listener;

public class SignFixerino implements Listener {

	public Main plugin;

	public SignFixerino(Main plugin) {
		this.plugin = plugin;
	}

	/*
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < event.getWorld().getMaxHeight() - 1; y++) {
				for (int z = 0; z < 15; z++) {
					Block block = event.getChunk().getBlock(x, y, z);
					if ((block.getState() instanceof Sign)) {
						Sign sign = (Sign)block.getState();
						String text = Arrays.toString(sign.getLines());
						if (text.length() > 100) {
							System.out.println("Deleting invalid signs at " + sign.getLocation().toString() + " :");
							sign.setLine(0, "???");
							sign.setLine(1, "???");
							sign.setLine(2, "???");
							sign.setLine(3, "???");
							sign.update();
						}
					}
				}
			}
		}
	}
	 */
}