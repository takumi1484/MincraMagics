package jp.mincra.mincramagics.event.player;

import jp.mincra.mincramagics.event.MincraListener;
import org.bukkit.entity.Player;

import java.util.EventListener;

public interface PlayerUseMagicRodEvent extends EventListener, MincraListener {

    /**
     * プレイヤーが魔法杖を使ったときに実行
     * @param player プレイヤー
     * @param mcr_id 魔法杖のID
     */
    void onPlayerUseMagicRod(Player player, String mcr_id);
}
