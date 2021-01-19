package jp.mincra.mincramagics;

import jp.mincra.mincramagics.command.MincraCommands;
import jp.mincra.mincramagics.listener.MincraListener;
import jp.mincra.mincramagics.entity.PlayerManager;
import jp.mincra.mincramagics.property.PropertyManager;
import jp.mincra.mincramagics.dao.SQLManager;
import jp.mincra.mincramagics.ui.UIManager;
import org.bukkit.plugin.java.JavaPlugin;

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
//        sqlManager.getConnection();
        sqlManager.createTable("CREATE TABLE IF NOT EXISTS player (" +
                //AUTO_INCREMENT 値が指定されなくても自動で入力される。
                "id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, " +
                "name varchar(20), " +
                "uuid VARBINARY(36) NOT NULL UNIQUE," +
                "mp_value FLOAT, " +
                "cooltime_value FLOAT, " +
                "cooltime_max FLOAT" +
                ")", "player");
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

        //SQL全て保存
        sqlManager.saveMincraPlayer();
    }

    public void onTick() {
        Timer timer = new Timer(); // 今回追加する処理
        TimerTask task = new TimerTask() {
            public void run() {
                // 定期的に実行したい処理
                uiManager.onTick();
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
