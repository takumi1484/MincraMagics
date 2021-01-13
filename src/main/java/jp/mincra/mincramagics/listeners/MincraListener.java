package jp.mincra.mincramagics.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class MincraListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        System.out.println("login");
        event.getPlayer().sendMessage("Hi~");
    }

    /**
    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        System.out.println("toggleflight");

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            Block b = player.getWorld().getBlockAt(player.getLocation().subtract(0, 2, 0));
            if (!b.getType().equals(Material.AIR)) {
                Vector v = player.getLocation().getDirection().multiply(1).setY(1);
                player.setVelocity(v);
            }
        }
    }
    */

}