package jp.mincra.mincramagics.managers;

import jp.mincra.mincramagics.MincraMagics;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UIManager {

    public void sendMPActionbar(){

        String actionbar;
        UUID uuid;
//        PlayerManager playerManager = MincraMagics.getPlayerManager();

        for (Player player : MincraMagics.getPlayerManager().getOnlinePlayerList()) {
            uuid = player.getUniqueId();
            actionbar = MincraMagics.getPlayerManager().getPlayerMP_value(uuid) + "/" +
                    MincraMagics.getPlayerManager().getPlayerMP_max(uuid);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionbar));
        }
    }
}
