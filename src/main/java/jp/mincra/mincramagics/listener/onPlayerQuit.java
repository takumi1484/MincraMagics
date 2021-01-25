package jp.mincra.mincramagics.listener;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class onPlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        //Mapから取得
        MincraPlayer mincraPlayer = MincraMagics.getPlayerManager().getMincraPlayerMap().get(uuid);
        //SQLに保存
        MincraMagics.getSQLManager().updateMincraPlayer(mincraPlayer);

        //オンラインプレイヤーリストを正しく設定するために遅延する
        Timer timer = new Timer(false);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //オンラインプレイヤーリストを設定
                MincraMagics.getPlayerManager().setOnlinePlayerList();
                //Mapから削除
                MincraMagics.getPlayerManager().removeMincraPlayer(uuid);
                timer.cancel();
            }
        };
        timer.schedule(task, 50);
    }
}
