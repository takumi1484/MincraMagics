package jp.mincra.mincramagics.listeners;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

public class MincraListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        System.out.println("login");
        UUID uuid = event.getPlayer().getUniqueId();
        MincraPlayer mincraPlayer = MincraMagics.getSQLManager().getMincraPlayer(uuid);
        System.out.println("[MincraMagics] name:"+mincraPlayer.getPlayerName()+" mp:"+mincraPlayer.getPlayerMP());
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