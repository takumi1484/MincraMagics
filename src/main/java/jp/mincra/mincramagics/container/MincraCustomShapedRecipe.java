package jp.mincra.mincramagics.container;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MincraCustomShapedRecipe {

    /**
     * @param itemStackList クラフト材料のリスト。
     * @param result クラフトの結果。
     */
    private List<ItemStack> itemStackList = new ArrayList<>(9);
    private ItemStack result;

    public void setItemStackList(int index, ItemStack element){
        itemStackList.add(index,element);
    }

    public List<ItemStack> getItemStackList(){
        return itemStackList;
    }

    public void setResult(ItemStack result){
        this.result = result;
    }

    public ItemStack getResult(){
        return result;
    }
}
