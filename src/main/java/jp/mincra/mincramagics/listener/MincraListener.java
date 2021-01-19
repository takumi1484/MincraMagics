package jp.mincra.mincramagics.listener;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MincraListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (event.getPlayer().hasPlayedBefore()){
            //1度ログイン済み

            //SQLから情報を取得
            MincraPlayer mincraPlayer = MincraMagics.getSQLManager().getMincraPlayer(player.getUniqueId());
            mincraPlayer.setPlayerName(player.getName());
            //プレイヤーをHashMapに追加
            MincraMagics.getPlayerManager().putMincraPlayer(mincraPlayer);

        } else {
            //初回ログイン
            System.out.println("[MincraMagics] "+player.getName()+"が初めてログインしました！");

            //新規のインスタンス
            MincraPlayer mincraPlayer = new MincraPlayer();
            mincraPlayer.setPlayerUUID(player.getUniqueId());
            mincraPlayer.setPlayerName(player.getName());
            //SQLに追加
            MincraMagics.getSQLManager().insertMincraPlayer(player.getUniqueId(),mincraPlayer);
            //プレイヤーをHashMapに追加
            MincraMagics.getPlayerManager().putMincraPlayer(mincraPlayer);

        }

        //オンラインプレイヤーリストを設定
        MincraMagics.getPlayerManager().setOnlinePlayerList();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

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