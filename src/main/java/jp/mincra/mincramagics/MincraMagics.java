package jp.mincra.mincramagics;

import jp.mincra.mincramagics.listeners.MincraListener;
import jp.mincra.mincramagics.managers.PlayerManager;
import jp.mincra.mincramagics.managers.PropertyManager;
import jp.mincra.mincramagics.managers.SQLManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class MincraMagics extends JavaPlugin {

    private static PlayerManager playerManager;
    private static PropertyManager propertyManager;
    private static SQLManager sqlManager;

    @Override
    public void onEnable() {

        //PropertyManager
        getPropertyManager();
        try {
            propertyManager.setProperty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        propertyManager.loadProperty();

        //PlayerManager
        getPlayerManager();

        //SQLManager
        getSQLManager();
        sqlManager.getConnection();

        //listener
        getServer().getPluginManager().registerEvents(new MincraListener(), this);
    }

    @Override
    public void onDisable() {

        //SQL切断
        sqlManager.closeConnection();
    }

    //playerManager取得
    public static PlayerManager getPlayerManager() {
        if (playerManager == null)
            playerManager = new PlayerManager();
        return playerManager;
    }
    //propertyManagerを取得
    public static PropertyManager getPropertyManager() {
        if (propertyManager == null)
            propertyManager = new PropertyManager();
        return propertyManager;
    }
    //sqlManagerを取得
    public static SQLManager getSQLManager() {
        if (sqlManager == null)
            sqlManager = new SQLManager();
        return sqlManager;
    }
}
