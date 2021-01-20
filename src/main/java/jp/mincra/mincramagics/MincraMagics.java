package jp.mincra.mincramagics;

import jp.mincra.mincramagics.command.MincraCommands;
import jp.mincra.mincramagics.listener.MincraListener;
import jp.mincra.mincramagics.entity.player.PlayerManager;
import jp.mincra.mincramagics.property.JsonManager;
import jp.mincra.mincramagics.property.PropertyManager;
import jp.mincra.mincramagics.dao.SQLManager;
import jp.mincra.mincramagics.ui.UIManager;
import jp.mincra.mincramagics.util.MincraChatUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;
import java.util.TimerTask;

public final class MincraMagics extends JavaPlugin {

    private static PlayerManager playerManager;
    public static PlayerManager getPlayerManager() {
        if (playerManager == null)
            playerManager = new PlayerManager();
        return playerManager;
    }

    private static PropertyManager propertyManager;
    public static PropertyManager getPropertyManager() {
        if (propertyManager == null)
            propertyManager = new PropertyManager();
        return propertyManager;
    }

    private static SQLManager sqlManager;
    public static SQLManager getSQLManager() {
        if (sqlManager == null)
            sqlManager = new SQLManager();
        return sqlManager;
    }

    private static UIManager uiManager;
    public static UIManager getUIManager(){
        if (uiManager == null)
            uiManager = new UIManager();
        return uiManager;
    }

    private static JsonManager jsonManager;
    public static JsonManager getJsonManager() {
        if (jsonManager == null)
            jsonManager = new JsonManager();
        return jsonManager;
    }


    protected static MincraMagics instance;
    public static MincraMagics getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

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
        //JsonManager
        getJsonManager();
        jsonManager.loadItemNode();

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

        instance = null;
        MincraChatUtil.sendConsoleMessage("プラグインが正常に終了しました。");
        setEnabled(false);
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

    public static void reload() {
        propertyManager.loadProperty();
        jsonManager.loadItemNode();
    }
}
