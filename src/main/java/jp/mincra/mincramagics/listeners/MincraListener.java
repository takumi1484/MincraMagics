package jp.mincra.mincramagics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class MincraListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        System.out.println("login");
        event.getPlayer().sendMessage("Hi~");
    }
}
