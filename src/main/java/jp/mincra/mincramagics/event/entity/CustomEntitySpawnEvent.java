package jp.mincra.mincramagics.event.entity;

import jp.mincra.mincramagics.event.MincraListener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.EventListener;

public interface CustomEntitySpawnEvent extends EventListener, MincraListener {

    /**
     * カスタムエンティティがスポーンしたときに実行
     * @param event イベント
     * @param mcr_id スポーンさせるエンティティ名
     */
    void onCustomEntitySpawn(EntitySpawnEvent event, String mcr_id);
}
