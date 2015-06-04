package net.dkcraft.opticore.util;

import net.dkcraft.opticore.Main;

public class Methods {

	public Main plugin;

	public Methods(Main plugin) {
		this.plugin = plugin;
	}

	// Check for int
	public boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}