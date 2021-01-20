package jp.mincra.mincramagics.command;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.entity.player.PlayerManager;
import jp.mincra.mincramagics.util.MincraChatUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class MincraCommands implements CommandExecutor {

    private JavaPlugin plugin;
    public MincraCommands(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Entity caster = null;

        if (sender instanceof Entity) caster = (Entity)sender;

        if (args.length < 1){
            //argsが空っぽの時の処理
            caster.sendMessage(MincraChatUtil.debug("引数が空です。"));
            return false;
        }

        switch (args[0]) {
            case "reload":
                MincraMagics.reload();
                sender.sendMessage(MincraChatUtil.debug("プラグインをリロードします..."));
                return true;

            case "test":
                if (caster instanceof Player) {
                    PlayerManager playerManager = MincraMagics.getPlayerManager();
                    playerManager.addPlayerMP_value(caster.getUniqueId(), -20);
                    playerManager.setPlayerCooltime(caster.getUniqueId(), 10);

//            ItemStack itemStack = caster.getInventory().getItemInMainHand();
//            NBTItem nbtItem = new NBTItem(itemStack);
//            caster.sendMessage(MincraChatUtil.makeDebugMessage(nbtItem.toString()));
//            NBTCompound nbtDisplay = nbtItem.addCompound("MincraMagics");
//            nbtDisplay.setString("id","daiyanoken");
//            caster.sendMessage(MincraChatUtil.makeDebugMessage(nbtDisplay.toString()));
//            caster.sendMessage(MincraChatUtil.makeDebugMessage(nbtItem.toString()));
//
//            itemStack = nbtItem.getItem();
//            int slot = caster.getInventory().getHeldItemSlot();
//            caster.getInventory().setItem(slot,itemStack);
//
//                    caster.sendMessage(MincraChatUtil.debug(MincraMagics.getJsonManager().getItemNode().get(1).get("mcr_id").toString()));

                    return true;

                }

            case "item":

                switch (args[1]) {
                    case "get":
                        if (caster instanceof Player) {
                            if (MincraMagics.getItemManager().getItem(args[2]) != null) {

                                caster.sendMessage(MincraChatUtil.debug(args[2]+"を付与しました。"));
                                ((Player) caster).getInventory().addItem(MincraMagics.getItemManager().getItem(args[2]));
                            } else {
                                caster.sendMessage(MincraChatUtil.debug(args[2]+"は未登録のアイテムです。"));
                            }
                            return true;

                        } else {
                            caster.sendMessage(MincraChatUtil.debug("/mcr item getはプレイヤーのみ実行可能です。"));
                            return false;

                        }
                }
        }
        return false;
    }
}
