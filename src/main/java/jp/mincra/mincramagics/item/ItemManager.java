package jp.mincra.mincramagics.item;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.ChatUtil;
import jp.mincra.mincramagics.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class ItemManager {

    private Map<String, ItemStack> itemStackMap = new HashMap<>();

    public void registerItem(JSONArray itemArray, String path) {

        itemStackMap = new HashMap<>();;

        ChatUtil.sendConsoleMessage("アイテムの登録を開始します...");

//        JSONArray itemArray = new JSONArray(MincraMagics.getJSONManager().getItemNode().toString());

        ItemStack item;
        Material material = null;
        String mcr_id;
        ItemMeta recipeIngredientMeta;
        List<String> recipeIngredientLoreList;

        JSONObject itemObject;
        JSONObject itemNBTObject;
        JSONObject itemDisplayObject;
        JSONArray itemEnchantmentsArray;
        JSONObject itemEachEnchant;
//                    JSONArray itemAttributeModifiersArray;
//                    JSONObject itemEachAttribute;
        JSONObject itemMincraMagicsObject = null;
        JSONObject itemRecipeObject = null;
        JSONArray itemRecipeShapeArray;

        NBTItem nbtItem;
        NBTCompound nbtDisplay;
        NBTList nbtLore;
        NBTCompoundList nbtEnchantments;
        NBTCompound nbtEachEnchant;
        NBTCompound nbtMincraMagics;
//                    NBTCompoundList nbtAttributeModifiers;
//                    NBTCompound nbtEachAttribute = null;


        int item_amount = 0;
        int recipe_amount = 0;

        for(int i=0, len=itemArray.length(); i<len; i++) {
            itemObject = itemArray.getJSONObject(i);

            //id-------------------------------------↓
            if (itemObject.has("id")) {
                if (itemObject.get("id") instanceof String) {
                    material = material.getMaterial(itemObject.getString("id").toUpperCase());
                    if (material != null) {
                        item = new ItemStack(Objects.requireNonNull(material));
                    } else {
                        ChatUtil.sendConsoleMessage("エラー: "+path+"の" + i + "番目のアイテムIDが無効です。 id: " + itemObject.getString("id"));
                        continue;
                    }
                } else {
                    ChatUtil.sendConsoleMessage("エラー: "+path+"の" + i + "番目のアイテムIDがString型である必要があります。 id: " + itemObject.get("id"));
                    continue;
                }
            } else {
                ChatUtil.sendConsoleMessage("エラー: "+path+"の" + i + "番目のアイテムIDが未指定です。");
                continue;
            }
            //---------------------------------------↑

            //mcr_id---------------------------------↓
            if (itemObject.has("mcr_id")) {
                if (itemObject.get("mcr_id") instanceof String) {
                    mcr_id = itemObject.getString("mcr_id");

                } else {
                    ChatUtil.sendConsoleMessage("エラー: "+path+"の" + i + "番目のMCR_IDがString型である必要があります。 id: " + itemObject.get("mcr_id"));
                    continue;
                }
            } else {
                ChatUtil.sendConsoleMessage("エラー: "+path+"の" + i + "番目のMCR_IDが未指定です。");
                continue;
            }
            //---------------------------------------↑

            //-nbt----------------------------------↓↓
            nbtItem = new NBTItem(item);

            //-mcr_id------------------------------↓↓↓
            nbtMincraMagics = nbtItem.addCompound("MincraMagics");
            nbtMincraMagics.setString("id", mcr_id);
            //-------------------------------------↑↑↑

            if (itemObject.has("nbt") && itemObject.get("nbt") instanceof JSONObject) {
                itemNBTObject = itemObject.getJSONObject("nbt");

                //-MincraMagics------------------------↓↓↓
//            itemMincraMagicsObject = itemNBTObject.getJSONObject("MincraMagics");
                //id
//            if (itemMincraMagicsObject.has("id") && itemMincraMagicsObject.get("id") instanceof String) {
//                nbtMincraMagics.setString("id", itemMincraMagicsObject.getString("id"));
//            }
                //-------------------------------------↑↑↑

                //-display-----------------------------↓↓↓
                itemDisplayObject = itemNBTObject.getJSONObject("display");
                nbtDisplay = nbtItem.addCompound("display");
                //Name
                if (itemDisplayObject.has("Name") && itemDisplayObject.get("Name") instanceof String) {
                    nbtDisplay.setString("Name", itemDisplayObject.getString("Name"));
                }
                //Lore
                if (itemDisplayObject.has("Lore") && itemDisplayObject.get("Lore") instanceof JSONArray) {
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

                        if (itemEachEnchant.has("id") && itemEachEnchant.get("id") instanceof String) {
                            nbtEachEnchant.setString("id", itemEachEnchant.getString("id"));
                        }

                        if (itemEachEnchant.has("lvl") && itemEachEnchant.get("lvl") instanceof Integer) {
                            nbtEachEnchant.setInteger("lvl", itemEachEnchant.getInt("lvl"));
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
            }
            //--------------------------------------↑↑


            item = nbtItem.getItem();

            item_amount = item_amount + 1;
            itemStackMap.put(mcr_id, item);
        }

        for(int i=0, len=itemArray.length(); i<len; i++) {
            itemObject = itemArray.getJSONObject(i);
            mcr_id = itemObject.getString("mcr_id");

            //レシピ登録
            NamespacedKey key = new NamespacedKey(MincraMagics.getInstance(), mcr_id);
            //一度登録解除
            Bukkit.removeRecipe(key);

            if (itemObject.has("craftable") && itemObject.getBoolean("craftable")) {
                if (itemObject.has("recipe"))
                    itemRecipeObject = itemObject.getJSONObject("recipe");
                    if (itemRecipeObject.has("shape")
                        && itemRecipeObject.get("shape") instanceof JSONArray
                        && ((JSONArray) itemRecipeObject.get("shape")).length() == 3) {
                    itemRecipeShapeArray = (JSONArray) itemRecipeObject.get("shape");

                    ShapedRecipe recipe = new ShapedRecipe(key, getItem(itemObject.getString("mcr_id")));

                    recipe.shape(itemRecipeShapeArray.get(0).toString(),
                            itemRecipeShapeArray.get(1).toString(),
                            itemRecipeShapeArray.get(2).toString());

                    StringBuffer shapeBuffer = new StringBuffer(itemRecipeShapeArray.get(0).toString());
                    shapeBuffer.append(itemRecipeShapeArray.get(1).toString());
                    shapeBuffer.append(itemRecipeShapeArray.get(2).toString());
                    String shape = shapeBuffer.toString();

                    List<Character> charList = StringUtil.getContainsCharacter(shape);

                    for (int j=0, charListSize=charList.size(); j<charListSize; j++) {
                        if (itemRecipeObject.getJSONObject("ingredient").has(charList.get(j).toString())
                                && itemRecipeObject.getJSONObject("ingredient").get(charList.get(j).toString()) instanceof String) {
                            recipe.setIngredient(charList.get(j),
                                    Objects.requireNonNull(material.getMaterial(itemRecipeObject.getJSONObject("ingredient").getString(charList.get(j).toString()).toUpperCase())));
                        }
                    }

                        Bukkit.addRecipe(recipe);
                        recipe_amount = recipe_amount + 1;

                } else {
                    ChatUtil.sendConsoleMessage("エラー: "+path+"のレシピが不適切です。 mcr_id: " + mcr_id);

                }
            }
        }

        ChatUtil.sendConsoleMessage("アイテムの登録が完了しました。 ");
        ChatUtil.sendConsoleMessage(item_amount + "個のアイテムと" + recipe_amount + "個のレシピを登録しました。");
    }

    public ItemStack getItem(String mcr_id) {
        return itemStackMap.get(mcr_id);
    }
}
