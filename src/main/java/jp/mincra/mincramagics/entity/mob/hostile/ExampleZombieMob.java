package jp.mincra.mincramagics.entity.mob.hostile;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.event.entity.CustomEntitySpawnEvent;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ExampleZombieMob implements CustomEntitySpawnEvent {

    @Override
    public void onCustomEntitySpawn(EntitySpawnEvent event, String mcr_id) {
//
//        if (mcr_id.equals("example_zombie")) {
//
//            Zombie zombie = (Zombie) event.getEntity();
//
//            NBTEntity nbtZombie = new NBTEntity(zombie);
//            nbtZombie.mergeCompound(new NBTContainer(MincraMagics.getMobManager().getMobNBT(mcr_id).toString()));
//        }
    }
}
