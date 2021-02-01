package jp.mincra.mincramagics.listener;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;
import jp.mincra.mincramagics.container.MincraSkill;
import jp.mincra.mincramagics.util.BossBarUtil;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.NamespacedKey;
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
            MincraPlayer mincraPlayer = MincraMagics.getSQLManager().getMincraPlayerSQL().getMincraPlayer(player.getUniqueId());
            mincraPlayer.setPlayerName(player.getName());
            //プレイヤーをHashMapに追加
            MincraMagics.getPlayerManager().putMincraPlayer(mincraPlayer);

            if (MincraMagics.getPlayerManager().getPlayerCooltime_value(player.getUniqueId()) > 0) {
                BossBarUtil.setCooltimeBossBar(player,MincraMagics.getPlayerManager().getCooltimeTitle(player.getUniqueId()),MincraMagics.getPlayerManager().getPlayerCooltime_value(player.getUniqueId()),false);
            }

        } else {
            //初回ログイン
            ChatUtil.sendConsoleMessage(""+player.getName()+"が初めてログインしました！");

            //新規のインスタンス
            MincraPlayer mincraPlayer = new MincraPlayer();
            mincraPlayer.setPlayerUUID(player.getUniqueId());
            mincraPlayer.setPlayerName(player.getName());
            //SQLに追加
            MincraMagics.getSQLManager().getMincraPlayerSQL().insertMincraPlayer(player.getUniqueId(),mincraPlayer);
            //プレイヤーをHashMapに追加
            MincraMagics.getPlayerManager().putMincraPlayer(mincraPlayer);

        }

        //初期レシピ解放
        MincraMagics.getItemManager().discoverShowRecipes(player);

        //オンラインプレイヤーリストを設定
        MincraMagics.getPlayerManager().setOnlinePlayerList();
        player.setAllowFlight(true);
    }
}
