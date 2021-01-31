package jp.mincra.mincramagics.event;

import org.bukkit.entity.Player;

import java.util.EventListener;

public interface PlayerUseMagicRodEvent extends EventListener,MincraListener {

    /**
     * プレイヤーが魔法杖を使ったときに実行
     * @param player プレイヤー
     * @param mcr_id 魔法杖のID
     */
    public void onPlayerUseMagicRod(Player player, String mcr_id);
}
