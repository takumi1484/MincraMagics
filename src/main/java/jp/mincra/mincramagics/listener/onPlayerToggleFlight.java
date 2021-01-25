package jp.mincra.mincramagics.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class onPlayerToggleFlight implements Listener {

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        Vector vector = new Vector(player.getVelocity().getX()*8,1,player.getVelocity().getZ()*8);
        e.getPlayer().setVelocity(vector);
        e.setCancelled(true);
    }
}
