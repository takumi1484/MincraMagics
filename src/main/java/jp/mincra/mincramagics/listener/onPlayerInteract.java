package jp.mincra.mincramagics.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import jp.mincra.mincramagics.skill.SkillInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class onPlayerInteract extends SkillInstance implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        //二度実行されるのを防止
        //https://www.spigotmc.org/threads/playerinteractevent-being-called-twice-with-tools.418345/
        if (e.getHand() != EquipmentSlot.HAND) return;

        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            final Player player = e.getPlayer();

            if (player.getInventory().getItemInMainHand() != null) {
                NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());

                if (nbtItem.getCompound("MincraMagics").hasKey("id")) {

                    switch (nbtItem.getCompound("MincraMagics").getString("id")) {
                        case "rod_move_1":
                            getMoveRod().MoveOne(player);
                            break;
                    }
                }
            }
        }
    }
}
