package jp.mincra.mincramagics.command;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.ChatUtil;
import jp.mincra.mincramagics.skill.MincraParticle;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
            caster.sendMessage(ChatUtil.debug("引数が空です。"));
            return false;
        }

        switch (args[0]) {
            case "reload":
                MincraMagics.reload();
                sender.sendMessage(ChatUtil.debug("プラグインをリロードします..."));
                return true;

            case "test":
                if (caster instanceof Player) {

                    MincraParticle mincraParticle = new MincraParticle();
                    mincraParticle.setRadius(Double.parseDouble(args[2]));
                    mincraParticle.setParticle(Particle.SPELL_INSTANT,Particle.FLAME,Particle.SOUL_FIRE_FLAME);

                    mincraParticle.drawMagicCircle(caster.getLocation(), Integer.parseInt(args[1]), 1);

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

                                caster.sendMessage(ChatUtil.debug(args[2]+"を付与しました。"));
                                ChatUtil.sendConsoleMessage(caster.getName()+"に"+args[2]+"を付与しました。");
                                ((Player) caster).getInventory().addItem(MincraMagics.getItemManager().getItem(args[2]));
                            } else {
                                caster.sendMessage(ChatUtil.debug(args[2]+"は未登録のアイテムです。"));
                            }
                            return true;

                        } else {
                            caster.sendMessage(ChatUtil.debug("/mcr item getはプレイヤーのみ実行可能です。"));
                            return false;

                        }
                }
        }
        return false;
    }
}
