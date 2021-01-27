package jp.mincra.mincramagics.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import jp.mincra.mincramagics.skill.SkillInstance;
import jp.mincra.mincramagics.skill.rod.MoveRod;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class onPlayerInteract extends SkillInstance implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        //二度実行されるのを防止
        //https://www.spigotmc.org/threads/playerinteractevent-being-called-twice-with-tools.418345/
        if(e.getHand() == EquipmentSlot.HAND) {

            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                final Player player = e.getPlayer();
                final ItemStack item = player.getInventory().getItemInMainHand();

                if (item.getType() != Material.AIR) {
                    NBTItem nbtItem = new NBTItem(item);

                    if (nbtItem.hasKey("MincraMagics")) {
                        switch (nbtItem.getCompound("MincraMagics").getString("id")) {
                            case "rod_move_1":
                                e.setCancelled(true);
                                getMoveRod().Move(player,1);
                                break;

                            case "rod_move_2":
                                e.setCancelled(true);
                                getMoveRod().Move(player,2);
                                break;

                            case "rod_move_3":
                                e.setCancelled(true);
                                getMoveRod().Move(player,3);
                                break;
                        }
                    }
                }
            }
        }
    }
}
