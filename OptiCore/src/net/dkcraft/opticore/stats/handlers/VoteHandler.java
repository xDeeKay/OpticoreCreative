package net.dkcraft.opticore.stats.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.MySQL;

public class VoteHandler implements Listener {

	public Main plugin;
	private MySQL mysql;
	
	public VoteHandler(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.NORMAL)
	public void onVotifierEvent(VotifierEvent event) {
		Vote vote = event.getVote();
		String playerName = vote.getUsername();
		final String uuid = Bukkit.getOfflinePlayer(playerName).getUniqueId().toString();

		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
			public void run() {
				if (mysql.playerDataContainsUUID(uuid)) {
					try {
						int previousTotalVotes = 0;

						PreparedStatement sql = mysql.connection
								.prepareStatement("SELECT total_votes FROM `player_stats` WHERE uuid=?;");
						sql.setString(1, uuid);

						ResultSet result = sql.executeQuery();
						result.next();

						previousTotalVotes = result.getInt("total_votes");

						PreparedStatement votesUpdate = mysql.connection
								.prepareStatement("UPDATE `player_stats` SET total_votes=? WHERE uuid=?;");
						votesUpdate.setInt(1, previousTotalVotes + 1);
						votesUpdate.setString(2, uuid);
						votesUpdate.executeUpdate();

						votesUpdate.close();
						sql.close();
						result.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
