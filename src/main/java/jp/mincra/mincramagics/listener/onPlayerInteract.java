package jp.mincra.mincramagics.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import jp.mincra.mincramagics.skill.SkillInstance;
import jp.mincra.mincramagics.skill.rod.ExpRod;
import jp.mincra.mincramagics.skill.rod.JumpRod;
import jp.mincra.mincramagics.skill.rod.MoveRod;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class onPlayerInteract implements Listener {

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
                        e.setCancelled(true);
                        switch (nbtItem.getCompound("MincraMagics").getString("id")) {
                            case "rod_move_1":
                                MoveRod moveRod1 = new MoveRod();
                                moveRod1.Move(player,1);
                                break;

                            case "rod_move_2":
                                MoveRod moveRod2 = new MoveRod();
                                moveRod2.Move(player,2);
                                break;

                            case "rod_move_3":
                                MoveRod moveRod3 = new MoveRod();
                                moveRod3.Move(player,3);
                                break;

                            case "rod_jump_1":
                                JumpRod jumpRod1 = new JumpRod();
                                jumpRod1.JumpOne(player);
                                break;

                            case "rod_jump_2":
                                JumpRod jumpRod2 = new JumpRod();
                                jumpRod2.JumpTwo(player);
                                break;

                            case "rod_jump_3":
                                JumpRod jumpRod3 = new JumpRod();
                                jumpRod3.JumpThree(player);
                                break;

                            case "rod_exp_1":
                                ExpRod expRod1 = new ExpRod();
                                expRod1.ExpOne(player);
                                break;

                            case "rod_exp_2":
                                ExpRod expRod2 = new ExpRod();
                                expRod2.ExpTwo(player);
                                break;

                            case "rod_exp_3":
                                ExpRod expRod3 = new ExpRod();
                                expRod3.ExpThree(player);
                                break;
                        }
                    }
                }
            }
        }
    }
}
