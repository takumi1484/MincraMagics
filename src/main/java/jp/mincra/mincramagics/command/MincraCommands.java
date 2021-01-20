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

        switch (args[0]){
            case "reload":
                MincraMagics.reload();
                sender.sendMessage(MincraChatUtil.debug("リロード中..."));
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

                    Inventory inventory = ((Player) caster).getInventory();
                    JSONArray itemArray = new JSONArray(MincraMagics.getJsonManager().getItemNode().toString());

                    ItemStack item;
                    Material material = null;

                    JSONObject itemObject;
                    JSONObject itemNBTObject;
                    JSONObject itemDisplayObject;
                    JSONArray itemEnchantmentsArray;
                    JSONObject itemEachEnchant;
//                    JSONArray itemAttributeModifiersArray;
//                    JSONObject itemEachAttribute;

                    NBTItem nbtItem;
                    NBTCompound nbtDisplay;
                    NBTList nbtLore;
                    NBTCompoundList nbtEnchantments;
                    NBTCompound nbtEachEnchant = null;
//                    NBTCompoundList nbtAttributeModifiers;
//                    NBTCompound nbtEachAttribute = null;

                    for(int i=0, len=itemArray.length(); i<len; i++) {
                        itemObject = itemArray.getJSONObject(i);
                        itemNBTObject = itemObject.getJSONObject("nbt");
                        itemDisplayObject = itemNBTObject.getJSONObject("display");

                        material = material.getMaterial(itemObject.getString("id").toUpperCase());
                        item = new ItemStack(Objects.requireNonNull(material));

                        nbtItem = new NBTItem(item);

                        //--display------------------------------↓
                        nbtDisplay = nbtItem.addCompound("display");
                        //Name
                        if (itemDisplayObject.has("Name") && itemDisplayObject.get("Name") instanceof String) {
                            nbtDisplay.setString("Name", itemDisplayObject.getString("Name"));
                        }
                        //Lore
                        if (itemDisplayObject.has("Lore") && itemDisplayObject.get("Lore") instanceof String) {
                            nbtLore = nbtDisplay.getStringList("Lore");
                            for (int j = 0, loreLen = itemDisplayObject.getJSONArray("Lore").length(); j < loreLen; j++) {
                                nbtLore.add(itemDisplayObject.getJSONArray("Lore").get(j));
                            }
                        }
                        //---------------------------------------↑

                        //CustomModelData
                        if (itemNBTObject.has("CustomModelData") && itemNBTObject.get("CustomModelData") instanceof Integer) {
                            nbtDisplay.setInteger("CustomModelData", itemNBTObject.getInt("CustomModelData"));
                        }

                        //HideFlags
                        if (itemNBTObject.has("HideFlags") && itemNBTObject.get("HideFlags") instanceof Integer) {
                            nbtDisplay.setInteger("HideFlags", itemNBTObject.getInt("HideFlags"));
                        }

                        //-Enchantments--------------------------↓
                        if (itemNBTObject.has("Enchantments") && itemNBTObject.get("Enchantments") instanceof JSONArray) {
                            itemEnchantmentsArray = itemNBTObject.getJSONArray("Enchantments");
                            nbtEnchantments = nbtItem.getCompoundList("Enchantments");
                            for (int j = 0, enLen = itemEnchantmentsArray.length(); j < enLen; j++) {
                                itemEachEnchant = ((JSONObject) itemEnchantmentsArray.get(j));
                                nbtEachEnchant = nbtItem.addCompound("Enchantments");

                                if (itemEachEnchant.has("id") && itemEachEnchant.get("id") instanceof String){
                                    nbtEachEnchant.setString("id",itemEachEnchant.getString("id"));
                                }

                                if (itemEachEnchant.has("lvl") && itemEachEnchant.get("lvl") instanceof Integer){
                                    nbtEachEnchant.setInteger("lvl",itemEachEnchant.getInt("lvl"));
                                }

                                nbtEnchantments.addCompound(nbtEachEnchant);
                            }
                        }
                        //---------------------------------------↑


                        //-AttributeModifiers--------------------↓
//                        if (itemNBTObject.has("AttributeModifiers") && itemNBTObject.get("AttributeModifiers") instanceof JSONArray) {
//                            itemAttributeModifiersArray = itemNBTObject.getJSONArray("AttributeModifiers");
//                            nbtAttributeModifiers = nbtItem.getCompoundList("AttributeModifiers");
//                            for (int j = 0, atrLen = itemAttributeModifiersArray.length(); j < atrLen; j++) {
//                                itemEachAttribute = ((JSONObject) itemAttributeModifiersArray.get(j));
//                                nbtEachAttribute = nbtItem.addCompound("AttributeModifiers");
//
//                                if (itemEachAttribute.has("AttributeName") && itemEachAttribute.get("AttributeName") instanceof String){
//                                    nbtEachAttribute.setString("AttributeName",itemEachAttribute.getString("AttributeName"));
//                                }
//
//                                if (itemEachAttribute.has("Name") && itemEachAttribute.get("Name") instanceof String){
//                                    nbtEachAttribute.setString("Name",itemEachAttribute.getString("Name"));
//                                }
//
//                                if (itemEachAttribute.has("Amount") && itemEachAttribute.get("Amount") instanceof Integer){
//                                    nbtEachAttribute.setInteger("Amount",itemEachAttribute.getInt("Amount"));
//                                }
//
//                                if (itemEachAttribute.has("Operation") && itemEachAttribute.get("Operation") instanceof Integer){
//                                    nbtEachAttribute.setInteger("Operation",itemEachAttribute.getInt("Operation"));
//                                }
//
//                                nbtAttributeModifiers.addCompound(nbtEachAttribute);
//                            }
//                        }
                        //---------------------------------------↑

                        item = nbtItem.getItem();

                        caster.sendMessage(MincraChatUtil.debug("アイテムgive"));
                        inventory.addItem(item);
                    }

                    return true;

                }
        }
        return false;
    }
}
