package jp.mincra.mincramagics.item;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraCustomShapedRecipe;
import jp.mincra.mincramagics.container.MincraSkill;
import jp.mincra.mincramagics.util.ChatUtil;
import jp.mincra.mincramagics.util.MathUtil;
import jp.mincra.mincramagics.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class ItemManager {

    private Map<String, ItemStack> itemStackMap = new HashMap<>();
    private Map<String, ShapedRecipe> shapedRecipeMap = new HashMap<>();
    private Map<String, MincraCustomShapedRecipe> customShapedRecipeMap = new HashMap<>();
    private Collection<NamespacedKey> showRecipeList = new ArrayList<>();

    public void register(Map<String,JSONArray> jsonArrayMap){
        itemStackMap = new HashMap<>();
        shapedRecipeMap = new HashMap<>();
        customShapedRecipeMap = new HashMap<>();

        jsonArrayMap.forEach((k, v) -> {
            registerItem(v,k);
            registerRecipe(v,k);
        });
    }

    public void registerItem(JSONArray itemArray, String path) {

        ChatUtil.sendConsoleMessage("アイテムの登録を開始します... from: "+path );

//        JSONArray itemArray = new JSONArray(MincraMagics.getJSONManager().getItemNode().toString());

        ItemStack item;
        Material material = null;
        String mcr_id;

        JSONObject itemObject;
        JSONObject itemNBTObject;
        JSONObject itemDisplayObject;
        JSONArray itemEnchantmentsArray;
        JSONObject itemEachEnchant;

        NBTItem nbtItem;
        NBTCompound nbtDisplay;
        NBTList nbtLore;
        NBTCompoundList nbtEnchantments;
        NBTCompound nbtEachEnchant;
        NBTCompound nbtMincraMagics;

        for (int i = 0, len = itemArray.length(); i < len; i++) {
            itemObject = itemArray.getJSONObject(i);

            //id-------------------------------------↓
            if (itemObject.has("id")) {
                if (itemObject.get("id") instanceof String) {
                    material = material.getMaterial(itemObject.getString("id").toUpperCase());
                    if (material != null) {
                        item = new ItemStack(Objects.requireNonNull(material));
                    } else {
                        ChatUtil.sendConsoleMessage("エラー: " + path + "の" + i + "番目のアイテムIDが無効です。 id: " + itemObject.getString("id"));
                        continue;
                    }
                } else {
                    ChatUtil.sendConsoleMessage("エラー: " + path + "の" + i + "番目のアイテムIDがString型である必要があります。 id: " + itemObject.get("id"));
                    continue;
                }
            } else {
                ChatUtil.sendConsoleMessage("エラー: " + path + "の" + i + "番目のアイテムIDが未指定です。");
                continue;
            }
            //---------------------------------------↑

            //mcr_id---------------------------------↓
            if (itemObject.has("mcr_id")) {
                if (itemObject.get("mcr_id") instanceof String) {
                    mcr_id = itemObject.getString("mcr_id");

                } else {
                    ChatUtil.sendConsoleMessage("エラー: " + path + "の" + i + "番目のMCR_IDがString型である必要があります。 id: " + itemObject.get("mcr_id"));
                    continue;
                }
            } else {
                ChatUtil.sendConsoleMessage("エラー: " + path + "の" + i + "番目のMCR_IDが未指定です。");
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

            //スキルアイテムのLore
            if (MincraMagics.getSkillManager().getSkillMap().containsKey(mcr_id)) {
                MincraSkill mincraSkill = MincraMagics.getSkillManager().getSkillMap().get(mcr_id);
                ItemMeta itemMeta = item.getItemMeta();

                StringBuilder spaceCooltime = new StringBuilder(" ");
                if (MathUtil.getPrecision(mincraSkill.getCooltime()) < 2) {
                    spaceCooltime.append(" ");
                }

                StringBuilder spaceExp = new StringBuilder("        ");
                if (MathUtil.getPrecision((float) mincraSkill.getExp_lv()) < 2) {
                    spaceExp.append(" ");
                }

                StringBuilder spaceBreak = new StringBuilder("     ");
                if (MathUtil.getPrecision(mincraSkill.getBreak_rate()) <= 2) {
                    spaceBreak.append(" ");
                }

                List<String> loreList = Arrays.asList(
                        ChatColor.AQUA + mincraSkill.getLore(),
                        ChatColor.AQUA + "クールタイム:" + spaceCooltime.toString() + ChatColor.AQUA + mincraSkill.getCooltime(),
                        ChatColor.AQUA + "MP消費:" + spaceExp.toString() + ChatColor.AQUA + mincraSkill.getExp_lv(),
                        ChatColor.DARK_RED + "崩壊確率:" + spaceBreak.toString() + ChatColor.DARK_RED + mincraSkill.getBreak_rate()
                );
                itemMeta.setLore(loreList);
                item.setItemMeta(itemMeta);
            }

            itemStackMap.put(mcr_id, item);
        }
        ChatUtil.sendConsoleMessage(itemStackMap.size() + "個のアイテムを登録しました。");
    }

    public void registerRecipe(JSONArray itemArray, String path) {

        ChatUtil.sendConsoleMessage("レシピの登録を開始します... from: "+path);

        Material material;
        String mcr_id;
        String ingredient_id;
        MincraCustomShapedRecipe customRecipe;
        boolean containCustomIngredient;

        JSONObject itemObject;
        JSONObject itemRecipeObject = null;
        JSONArray itemRecipeShapeArray;

        for(int i=0, len=itemArray.length(); i<len; i++) {

            itemObject = itemArray.getJSONObject(i);
            mcr_id = itemObject.getString("mcr_id");
            customRecipe = new MincraCustomShapedRecipe();
            containCustomIngredient = false;

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

                    //カスタムレシピの形
                    customRecipe.shape(shape);
                    customRecipe.setResult(getItem(itemObject.getString("mcr_id")));

                    if (itemRecipeObject.has("show") && itemRecipeObject.getBoolean("show")) {
                        showRecipeList.add(key);
                    }

                    List<Character> charList = StringUtil.getContainsCharacter(shape);

                    for (int j=0, charListSize=charList.size(); j<charListSize; j++) {

                        if (itemRecipeObject.getJSONObject("ingredient").has(charList.get(j).toString())
                                && itemRecipeObject.getJSONObject("ingredient").get(charList.get(j).toString()) instanceof String) {

                            ingredient_id = itemRecipeObject.getJSONObject("ingredient").getString(charList.get(j).toString());

                            if (itemStackMap.containsKey(ingredient_id)) {

                                //カスタムレシピの場合
                                containCustomIngredient = true;
                                customRecipe.setIngredient(charList.get(j),itemStackMap.get(ingredient_id));
                                customRecipe.setItemStack();

                            } else {

                                //カスタムアイテムを使わないレシピの場合
                                material = Material.getMaterial(ingredient_id.toUpperCase());
                                if (material != null) {
                                    recipe.setIngredient(charList.get(j), material);

                                    //カスタムレシピの場合もあるかもなので毎時実行
                                    customRecipe.setIngredient(charList.get(j),new ItemStack(material));
                                }
                            }
                        }
                    }

                    if (containCustomIngredient) {

                        //カスタムレシピの場合
                        customShapedRecipeMap.put(mcr_id, customRecipe);

                    } else {

                        //カスタムアイテムを使わないレシピの場合
                        Bukkit.addRecipe(recipe);
                        shapedRecipeMap.put(mcr_id, recipe);
                    }


                } else {
                    ChatUtil.sendConsoleMessage("エラー: "+path+"のレシピが不適切です。 mcr_id: " + mcr_id);

                }
            }
        }

        ChatUtil.sendConsoleMessage(shapedRecipeMap.size() + "個のレシピを登録しました。");
        ChatUtil.sendConsoleMessage(customShapedRecipeMap.size() + "個のカスタムレシピを登録しました。");
    }

    public void discoverShowRecipes(Player player){
        player.discoverRecipes(showRecipeList);
    }

   public ItemStack getItem(String mcr_id) {
        return itemStackMap.get(mcr_id);
    }

    public Map<String, ItemStack> getItemStackMap() {
        return itemStackMap;
    }

    public ShapedRecipe getRecipe(String mcr_id) {
        return shapedRecipeMap.get(mcr_id);
    }

    public Map<String, MincraCustomShapedRecipe> getCustomShapedRecipeMap() {
        return customShapedRecipeMap;
    }
}
