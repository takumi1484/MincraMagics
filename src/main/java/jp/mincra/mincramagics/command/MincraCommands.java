package jp.mincra.mincramagics.command;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.entity.player.PlayerManager;
import jp.mincra.mincramagics.util.MincraChatUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MincraCommands implements CommandExecutor {

    private JavaPlugin plugin;
    public MincraCommands(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player caster;
        if (sender instanceof Player) {
            caster = (Player) sender;
            PlayerManager playerManager = MincraMagics.getPlayerManager();
            playerManager.addPlayerMP_value(caster.getUniqueId(), -20);
            playerManager.setPlayerCooltime(caster.getUniqueId(), 10);

            ItemStack itemStack = caster.getInventory().getItemInMainHand();
            NBTItem nbtItem = new NBTItem(itemStack);
            caster.sendMessage(MincraChatUtil.makeDebugMessage(nbtItem.toString()));
            NBTCompound nbtCompound = nbtItem.addCompound("MincraMagics");
            nbtCompound.setString("id","daiyanoken");
            caster.sendMessage(MincraChatUtil.makeDebugMessage(nbtCompound.toString()));
            caster.sendMessage(MincraChatUtil.makeDebugMessage(nbtItem.toString()));

            itemStack = nbtItem.getItem();
            int slot = caster.getInventory().getHeldItemSlot();
            caster.getInventory().setItem(slot,itemStack);

            return true;
        }
        return false;
    }
}
