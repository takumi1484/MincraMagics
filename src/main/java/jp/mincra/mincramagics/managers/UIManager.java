package jp.mincra.mincramagics.managers;

import jp.mincra.mincramagics.MincraMagics;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UIManager {

    public void onTick(){
        String actionbar;
        String subtitle;
        UUID uuid;
        PlayerManager playerManager = MincraMagics.getPlayerManager();

        for (Player player : playerManager.getOnlinePlayerList()) {
            uuid = player.getUniqueId();

            //MP
            String mp_value = String.format("%.1f",playerManager.getPlayerMP_value(uuid));
            String mp_max = String.format("%.1f",playerManager.getPlayerMP_max(uuid));
            actionbar = mp_value + "/" + mp_max;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionbar));

            //クールタイム減少
            if (playerManager.getPlayerCooltime_value(uuid) > 0f) {
                String cooltime_value = String.format("%.1f",playerManager.getPlayerCooltime_value(uuid));
                String cooltime_max = String.format("%.1f",playerManager.getPlayerCooltime_max(uuid));
                subtitle = cooltime_value + "/" + cooltime_max;
                player.sendTitle("", subtitle,0,10,10);
                playerManager.addPlayerCooltime_value(uuid,-0.05f);
            }
        }
    }
}
