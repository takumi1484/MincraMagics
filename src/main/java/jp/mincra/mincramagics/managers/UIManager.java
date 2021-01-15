package jp.mincra.mincramagics.managers;

import jp.mincra.mincramagics.MincraMagics;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UIManager {

    public void sendMPActionbar(){

        String actionbar = "test";
        for (Player player : MincraMagics.getPlayerManager().getOnlinePlayerList()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionbar));
        }
    }
}
