package jp.mincra.mincramagics.listener;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {

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
            ChatUtil.sendConsoleMessage(""+player.getName()+"が初めてログインしました！");

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
        player.setAllowFlight(true);
    }
}
