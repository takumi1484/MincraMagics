package jp.mincra.mincramagics.ui;

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

        for (Player player : MincraMagics.getPlayerManager().getOnlinePlayerList()) {
            uuid = player.getUniqueId();

            //MP
            String mp_value = String.format("%.1f",MincraMagics.getPlayerManager().getPlayerMP_value(uuid));
            String mp_max = String.format("%.1f",MincraMagics.getPlayerManager().getPlayerMP_max(uuid));
            actionbar = mp_value + "/" + mp_max;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionbar));

            //クールタイム減少
            if (MincraMagics.getPlayerManager().getPlayerCooltime_value(uuid) > 0f) {
                String cooltime_value = String.format("%.1f",MincraMagics.getPlayerManager().getPlayerCooltime_value(uuid));
                String cooltime_max = String.format("%.1f",MincraMagics.getPlayerManager().getPlayerCooltime_max(uuid));
                subtitle = cooltime_value + "/" + cooltime_max;
                player.sendTitle("", subtitle,0,10,10);
                MincraMagics.getPlayerManager().addPlayerCooltime_value(uuid,-0.05f);
            }
        }
    }
}
