package jp.mincra.mincramagics.listener;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraCustomShapedRecipe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class MincraPrepareItemCraftEvent implements Listener {

    @EventHandler
    public void customCrafting(PrepareItemCraftEvent e) {
        ItemStack[] itemStack = e.getInventory().getMatrix();

/*        //デバッグメッセ―ジ
        ChatUtil.sendConsoleMessage("recipe...");
        for (int i=0,len=itemStack.length; i<len ;i++){
            if (itemStack[i] != null){
                ChatUtil.sendConsoleMessage("len: "+len+" [i]: "+i+" itemstack: "+itemStack[i].toString());
            }
        }
*/

        MincraMagics.getItemManager().getCustomShapedRecipeMap().forEach((k, v) -> {
            if (isValidCustomShapedRecipe(itemStack,v)) {
                e.getInventory().setResult(v.getResult());
            }
        });
    }


    /**
     * ItemStack[]とMincraCustomShapedRecipeのレシピ配列が一致するかを検査します。
     * @param itemStack prepareItemCraftEvent.getInventory().getMatrix() で得られる配列
     * @param customShapedRecipe MincraCustomShapedRecipe
     * @return boolean
     */
    public boolean isValidCustomShapedRecipe(ItemStack[] itemStack, MincraCustomShapedRecipe customShapedRecipe) {
        boolean isValid = true;

        if (customShapedRecipe != null) {
//            ChatUtil.sendConsoleMessage("length: "+customShapedRecipe.getItemStack().length);

            for (int i = 0, len = customShapedRecipe.getItemStack().length; i < len; i++) {

                //レシピファイルではiスロットにアイテムがあるとき
                if (customShapedRecipe.getItemStack()[i] != null) {
//                    ChatUtil.sendConsoleMessage("\n"+customShapedRecipe.getItemStack()[i].toString());

                    //クラフトテーブルのiスロットにはアイテムがない
                    if (itemStack[i] == null) {
                        isValid = false;

                    } else {
                        //メタがあるとき
                        if (itemStack[i].hasItemMeta()) {
                            //クラフトテーブルのiスロットのアイテムが一致しない
                            if (!itemStack[i].getItemMeta().equals(customShapedRecipe.getItemStack()[i].getItemMeta())) {
                                isValid = false;
                            }
                        } else {
                            //アイテムidが違う
                            if (!itemStack[i].getType().equals(customShapedRecipe.getItemStack()[i].getType())) {
                                isValid = false;
                            }
                        }
                    }
                } else {
                    //クラフトテーブルのiスロットにはアイテムがある
                    if (itemStack[i] != null) {
                        isValid = false;
                    }
                }
            }
        }

        return isValid;
    }
}
