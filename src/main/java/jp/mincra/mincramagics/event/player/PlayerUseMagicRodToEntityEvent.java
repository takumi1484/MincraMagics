package jp.mincra.mincramagics.event.player;

import jp.mincra.mincramagics.event.MincraListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.EventListener;

public interface PlayerUseMagicRodToEntityEvent extends EventListener, MincraListener {

    /**
     * プレイヤーがエンティティに魔法杖を使ったときに実行
     * @param player プレイヤー
     * @param target 実行した対象
     * @param mcr_id 魔法杖のID
     */
    void run(Player player, Entity target, String mcr_id);
}
