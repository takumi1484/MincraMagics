package jp.mincra.mincramagics.util;

import jp.mincra.mincramagics.MincraMagics;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

public class BossBarUtil {

    //ボスバー
    private static BossBar bossBar = Bukkit.createBossBar(ChatUtil.translateHexColorCodes("&#d6eeff&f&lクールタイム"),
            BarColor.BLUE,
            BarStyle.SOLID);

    public static void setCooltimeBossBar(Player player, String skill_name, float cooltime_value) {

        MincraMagics.getPlayerManager().setPlayerCooltime(player.getUniqueId(),cooltime_value);

        if (skill_name != null || !skill_name.equals("")) {
            bossBar.setTitle(ChatUtil.translateHexColorCodes("&#bfe5ff&f[" + skill_name + "&#bfe5ff&f] &#d6eeff&f&lクールタイム"));
        }

        Timer timer = new Timer(); // 今回追加する処理
        TimerTask task = new TimerTask() {
            public void run() {

                if (MincraMagics.getPlayerManager().getPlayerCooltime_value(player.getUniqueId()) > 0) {
                    MincraMagics.getPlayerManager().addPlayerCooltime_value(player.getUniqueId(),-0.05f);

                    double progress = MincraMagics.getPlayerManager().getPlayerCooltime_value(player.getUniqueId())
                            / MincraMagics.getPlayerManager().getPlayerCooltime_max(player.getUniqueId());
                    if (progress < 0) {
                        progress = 0d;
                    }

                    bossBar.setProgress(progress);

                    bossBar.addPlayer(player);
                } else {
                    bossBar.removePlayer(player);
                    timer.cancel();
                }

            }
        };
        timer.scheduleAtFixedRate(task,0,100);
    }
}
