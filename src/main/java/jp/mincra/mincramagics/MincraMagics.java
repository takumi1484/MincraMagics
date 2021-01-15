package jp.mincra.mincramagics;

import jp.mincra.mincramagics.commands.MincraCommands;
import jp.mincra.mincramagics.listeners.MincraListener;
import jp.mincra.mincramagics.managers.PlayerManager;
import jp.mincra.mincramagics.managers.PropertyManager;
import jp.mincra.mincramagics.managers.SQLManager;
import jp.mincra.mincramagics.managers.UIManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.TimerTask;

public final class MincraMagics extends JavaPlugin {

    private static PlayerManager playerManager;
    private static PropertyManager propertyManager;
    private static SQLManager sqlManager;
    private static UIManager uiManager;

    @Override
    public void onEnable() {

        //PropertyManager
        getPropertyManager();
        propertyManager.setProperty();
        propertyManager.loadProperty();
        //PlayerManager
        getPlayerManager();
        playerManager.setOnlinePlayerList();
        //SQLManager
        getSQLManager();
        sqlManager.getConnection();
        sqlManager.createRequiredTables();
        //UIManager
        getUIManager();

        //listener
        onTick();
        getServer().getPluginManager().registerEvents(new MincraListener(), this);

        //command
        getCommand("mcr").setExecutor(new MincraCommands(this));
    }

    @Override
    public void onDisable() {

        //SQL全て保存&切断
        sqlManager.saveMincraPlayer();
        sqlManager.closeConnection();
    }

    public void onTick() {
        Timer timer = new Timer(); // 今回追加する処理
        TimerTask task = new TimerTask() {
            public void run() {
                // 定期的に実行したい処理
                uiManager.sendMPActionbar();
            }
        };
        timer.scheduleAtFixedRate(task,0,50);
    }


    public static PlayerManager getPlayerManager() {
        if (playerManager == null)
            playerManager = new PlayerManager();
        return playerManager;
    }
    public static PropertyManager getPropertyManager() {
        if (propertyManager == null)
            propertyManager = new PropertyManager();
        return propertyManager;
    }
    public static SQLManager getSQLManager() {
        if (sqlManager == null)
            sqlManager = new SQLManager();
        return sqlManager;
    }
    public static UIManager getUIManager(){
        if (uiManager == null)
            uiManager = new UIManager();
        return uiManager;
    }

}
