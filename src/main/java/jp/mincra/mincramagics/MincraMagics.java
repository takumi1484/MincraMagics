package jp.mincra.mincramagics;

import jp.mincra.mincramagics.command.MincraCommands;
import jp.mincra.mincramagics.entity.player.PlayerManager;
import jp.mincra.mincramagics.item.ItemManager;
import jp.mincra.mincramagics.listener.*;
import jp.mincra.mincramagics.property.JSONManager;
import jp.mincra.mincramagics.property.PropertyManager;
import jp.mincra.mincramagics.skill.SkillManager;
import jp.mincra.mincramagics.sql.SQLManager;
import jp.mincra.mincramagics.ui.UIManager;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.plugin.java.JavaPlugin;

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

    private static JSONManager jsonManager;
    public static JSONManager getJSONManager() {
        if (jsonManager == null)
            jsonManager = new JSONManager();
        return jsonManager;
    }

    private static ItemManager itemManager;
    public static ItemManager getItemManager() {
        if (itemManager == null)
            itemManager = new ItemManager();
        return itemManager;
    }

    private static SkillManager skillManager;
    public static SkillManager getSkillManager() {
        if (skillManager == null)
            skillManager = new SkillManager();
        return skillManager;
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
                "cooltime_max FLOAT, " +
                "cooltime_title TEXT" +
                ")", "player");
        //UIManager
        getUIManager();
        //JsonManager
        getJSONManager();
        //SkillManager
        getSkillManager();
        skillManager.register(jsonManager.getAllJSONArray("./plugins/MincraMagics/data/skills"));
        //ItemManager
        getItemManager();
        itemManager.register(jsonManager.getAllJSONArray("./plugins/MincraMagics/data/items"));

        //listener
//        onTick();
        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new onPlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new onPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new onPrepareItemCraft(), this);
        getServer().getPluginManager().registerEvents(new onPlayerToggleFlight(), this);
        getServer().getPluginManager().registerEvents(new onPlayerInteract(), this);

        //command
        getCommand("mcr").setExecutor(new MincraCommands(this));
    }

    @Override
    public void onDisable() {

        //SQL全て保存
        sqlManager.saveMincraPlayer();

        instance = null;
        ChatUtil.sendConsoleMessage("プラグインが正常に終了しました。");
        setEnabled(false);
    }

    public static void reload() {
        ChatUtil.sendConsoleMessage("プラグインをリロードします...");
        propertyManager.loadProperty();
//        jsonManager.loadItemNode();
        itemManager.register(jsonManager.getAllJSONArray("./plugins/MincraMagics/data/items"));
        skillManager.register(jsonManager.getAllJSONArray("./plugins/MincraMagics/data/skills"));
    }
}

        /*
       y↑
         H.----------.G :loc2
         /|         /|
       E.----------.F|
        |D.--------|-.C
        |/         |/
 loc1: A.----------.B  →x
       /
      /↗z
         */