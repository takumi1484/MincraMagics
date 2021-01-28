package jp.mincra.mincramagics.command;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.BossBarUtil;
import jp.mincra.mincramagics.util.ChatUtil;
import jp.mincra.mincramagics.skill.MincraParticle;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;
import java.util.TimerTask;

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
                    mincraParticle.setRadius(5);
                    mincraParticle.setParticle(Particle.SPELL_INSTANT);
                    mincraParticle.setAngle(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                    mincraParticle.drawMagicCircle(caster.getLocation(),5, 1,1,0,0);

                    /*
                    Timer timer = new Timer(); // 今回追加する処理
                    Entity finalCaster = caster;
                    final double[] i = {0};

                    TimerTask task = new TimerTask() {
                            public void run() {
                                i[0] += 0.1;

                                mincraParticle.setAngle(Double.parseDouble(args[1]) + i[0], Double.parseDouble(args[2]) + i[0], Double.parseDouble(args[3]) + i[0]);
                                mincraParticle.drawMagicCircle(finalCaster.getLocation(),5, 1,1,0,0);

                                if (i[0] > 50) {
                                    timer.cancel();
                                }
                            }
                        };
                    timer.scheduleAtFixedRate(task,0,100);
                    */

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
