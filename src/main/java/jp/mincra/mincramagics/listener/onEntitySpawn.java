package jp.mincra.mincramagics.listener;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class onEntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();

        //昇順のリストから
        TreeMap<Integer, String> chanceMap = MincraMagics.getMobManager().getTypeChanceMap(entity.getType());

        if (chanceMap != null) {
            Set<Integer> chanceSet = chanceMap.keySet();

            Random random = new Random();
            int hash = random.nextInt(MincraMagics.getMobManager().getTypeSum(entity.getType()));

            for (Integer chance : chanceSet) {
                //乱数が0以下ならそのときのmcr_idで実行
                hash = hash - chance;

                if (hash < 0) {

                    String mcr_id = chanceMap.get(chance);

                    NBTEntity nbtZombie = new NBTEntity(entity);
                    nbtZombie.mergeCompound(new NBTContainer(MincraMagics.getMobManager().getMobNBT(mcr_id).toString()));

                    MincraMagics.getEventNotifier().runCustomEntitySpawn(event, mcr_id);

                    break;
                }
            }
        }
    }
}
