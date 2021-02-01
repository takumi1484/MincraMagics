package jp.mincra.mincramagics.listener;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class onEntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {

        Collection<String> idCollection = MincraMagics.getMobManager().getChanceMap().keySet();

        int chanceSum = 0;

        for (String mcr_id : idCollection) {

            //スポーンしたモブのタイプとjsonのタイプが一致するかどうか
            if (event.getEntity().getType().equals(EntityType.valueOf(MincraMagics.getMobManager().getEntityJsonMap().get(mcr_id).getString("id").toUpperCase()))) {

                chanceSum = chanceSum + MincraMagics.getMobManager().getChanceMap().get(mcr_id);
            }
        }

        //昇順のリストから
        for(Map.Entry<String, Integer> entry : MincraMagics.getMobManager().getSortedChanceMap() ) {

            if (event.getEntity().getType().equals(EntityType.valueOf(MincraMagics.getMobManager().getEntityJsonMap().get(entry.getKey()).getString("id").toUpperCase()))) {

                chanceSum = chanceSum - entry.getValue();

                if (chanceSum <= 0) {
                    MincraMagics.getEventNotifier().runCustomEntitySpawn(event, entry.getKey());
                }
            }
        }
    }
}
