package jp.mincra.mincramagics.container;

import jp.mincra.mincramagics.item.ItemManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MincraCustomShapedRecipe {

    /**
     * @param itemStackList クラフト材料のリスト。
     * @param result クラフトの結果。
     */
    private ItemStack[] itemStack = new ItemStack[9];
    private ItemStack result;
    private String shape;
    private Map<Character, ItemStack> ingredientMap = new HashMap<>();

    public void setItemStack(){
        for (int i=0; i<9; i++) {
            if (ingredientMap.containsKey(shape.charAt(i))) {

                itemStack[i] = ingredientMap.get(shape.charAt(i));
            }
        }

        shape = null;
        ingredientMap = null;
    }

    public ItemStack[] getItemStack(){
        return itemStack;
    }

    public void setResult(ItemStack result){
        this.result = result;
    }

    public ItemStack getResult(){
        return result;
    }

    public void shape(String shape) {
        this.shape = shape;
    }

    public void setIngredient(Character character, ItemStack itemStack) {
        ingredientMap.put(character, itemStack);
    }
}
