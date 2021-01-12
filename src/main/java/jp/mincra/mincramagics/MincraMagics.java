package jp.mincra.mincramagics;

import jp.mincra.mincramagics.listeners.MincraListener;
import jp.mincra.mincramagics.util.PropertyUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MincraMagics extends JavaPlugin {

    private static PlayerManager playerManager;
    private static Map<String, String> PropertyMap;

    @Override
    public void onEnable() {

        //プロパティ
        try {
            PropertyUtil.setProperty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyUtil.getProperty();

        //PlayerManager
        getPlayerManager();

        //listenerの登録
        getServer().getPluginManager().registerEvents(new MincraListener(), this);
    }

    @Override
    public void onDisable() {
    }

    //pManagerを取得
    public static PlayerManager getPlayerManager() {
        if (playerManager == null)
            playerManager = new PlayerManager();
        return playerManager;
    }
    //PropertyMapを取得
    public static Map<String, String> getPropertyMap() {
        if (PropertyMap == null)
            PropertyMap = new HashMap<>();
        return PropertyMap;
    }
}
