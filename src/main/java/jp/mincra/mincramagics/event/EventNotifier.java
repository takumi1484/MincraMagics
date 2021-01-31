package jp.mincra.mincramagics.event;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EventNotifier {

    private List<PlayerUseMagicRodEvent> playerUseMagicRod = new ArrayList<>();

    /**
     * プレイヤーが魔法杖を使ったときのイベント
     * @param player 使用したプレイヤー
     * @param mcr_id 使用した杖のmcr_id
     */
    public void runPlayerUseMagicRod(Player player, String mcr_id) {
        if (this.playerUseMagicRod != null) {
            for (PlayerUseMagicRodEvent playerUseMagicRodEvent : playerUseMagicRod) {
                playerUseMagicRodEvent.onPlayerUseMagicRod(player, mcr_id);
            }
        }
    }

    /**
     * リスナーを追加する。
     * @param listener リスナーを実装したクラス
     */
    public void registerEvents(MincraListener listener) {
        if (listener instanceof PlayerUseMagicRodEvent) {
            playerUseMagicRod.add((PlayerUseMagicRodEvent) listener);
        }
    }
}
