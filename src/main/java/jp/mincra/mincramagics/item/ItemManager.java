package jp.mincra.mincramagics.item;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.MincraChatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemManager {

    private Map<String, ItemStack> itemStackMap = new HashMap<>();


    public void registerItem() {

        JSONArray itemArray = new JSONArray(MincraMagics.getJsonManager().getItemNode().toString());

        ItemStack item;
        Material material = null;
        String mcr_id;

        JSONObject itemObject;
        JSONObject itemNBTObject;
        JSONObject itemDisplayObject;
        JSONArray itemEnchantmentsArray;
        JSONObject itemEachEnchant;
//                    JSONArray itemAttributeModifiersArray;
//                    JSONObject itemEachAttribute;
        JSONObject itemMincraMagicsObject = null;

        NBTItem nbtItem;
        NBTCompound nbtDisplay;
        NBTList nbtLore;
        NBTCompoundList nbtEnchantments;
        NBTCompound nbtEachEnchant;
        NBTCompound nbtMincraMagics;
//                    NBTCompoundList nbtAttributeModifiers;
//                    NBTCompound nbtEachAttribute = null;

        for(int i=0, len=itemArray.length(); i<len; i++) {
            itemObject = itemArray.getJSONObject(i);
            itemNBTObject = itemObject.getJSONObject("nbt");

            //id-------------------------------------↓
            if (itemObject.has("id")) {
                if (itemObject.get("id") instanceof String) {
                    material = material.getMaterial(itemObject.getString("id").toUpperCase());
                    if (material != null) {
                        item = new ItemStack(Objects.requireNonNull(material));
                    } else {
                        MincraChatUtil.sendConsoleMessage("エラー: /item/items.jsonの" + i + "番目のアイテムIDが無効です。 id: " + itemObject.getString("id"));
                        continue;
                    }
                } else {
                    MincraChatUtil.sendConsoleMessage("エラー: /item/items.jsonの" + i + "番目のアイテムIDがString型である必要があります。 id: " + itemObject.get("id"));
                    continue;
                }
            } else {
                MincraChatUtil.sendConsoleMessage("エラー: /item/items.jsonの"+i+"番目のアイテムIDが未指定です。");
                continue;
            }
            //---------------------------------------↑

            //mcr_id---------------------------------↓
            if (itemObject.has("mcr_id")) {
                if (itemObject.get("mcr_id") instanceof String) {
                    mcr_id = itemObject.getString("mcr_id");

                } else {
                    MincraChatUtil.sendConsoleMessage("エラー: /item/items.jsonの" + i + "番目のMCR_IDがString型である必要があります。 id: " + itemObject.get("mcr_id"));
                    continue;
                }
            } else {
                MincraChatUtil.sendConsoleMessage("エラー: /item/items.jsonの"+i+"番目のMCR_IDが未指定です。");
                continue;
            }
            //---------------------------------------↑

            //-nbt----------------------------------↓↓
            nbtItem = new NBTItem(item);

            //-MincraMagics------------------------↓↓↓
//            itemMincraMagicsObject = itemNBTObject.getJSONObject("MincraMagics");
            nbtMincraMagics = nbtItem.addCompound("MincraMagics");
            //id
//            if (itemMincraMagicsObject.has("id") && itemMincraMagicsObject.get("id") instanceof String) {
//                nbtMincraMagics.setString("id", itemMincraMagicsObject.getString("id"));
//            }
            nbtMincraMagics.setString("id",mcr_id);
            //-------------------------------------↑↑↑

            //-display-----------------------------↓↓↓
            itemDisplayObject = itemNBTObject.getJSONObject("display");
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
            //-------------------------------------↑↑↑

            //CustomModelData
            if (itemNBTObject.has("CustomModelData") && itemNBTObject.get("CustomModelData") instanceof Integer) {
                nbtItem.setInteger("CustomModelData", itemNBTObject.getInt("CustomModelData"));
            }

            //HideFlags
            if (itemNBTObject.has("HideFlags") && itemNBTObject.get("HideFlags") instanceof Integer) {
                nbtItem.setInteger("HideFlags", itemNBTObject.getInt("HideFlags"));
            }

            //-Enchantments------------------------↓↓↓
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
            //-------------------------------------↑↑↑

            //-AttributeModifiers------------------↓↓↓
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
            //-------------------------------------↑↑↑

            //--------------------------------------↑↑


            item = nbtItem.getItem();

            itemStackMap.put(mcr_id, item);
        }
    }

    public ItemStack getItem(String mcr_id) {
        return itemStackMap.get(mcr_id);
    }
}
