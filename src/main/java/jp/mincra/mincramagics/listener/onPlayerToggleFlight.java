package jp.mincra.mincramagics.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class onPlayerToggleFlight implements Listener {

    @EventHandler
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        Vector vector = new Vector(player.getVelocity().getX()*3,0.7,player.getVelocity().getZ()*3);
        e.getPlayer().setVelocity(vector);
        e.setCancelled(true);
    }
}
