package jp.mincra.mincramagics;

import jp.mincra.mincramagics.util.PropertyUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

public final class MincraMagics extends JavaPlugin {

    private static PlayerManager pManager;

    public static Map<String, String> PropertyMap;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //プロパティ
        try {
            PropertyUtil.setProperty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyUtil.loadProperty();

        getPlayerManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //PlayerManagerをインスタンス化
    public static PlayerManager getPlayerManager() {
        if (pManager == null)
            pManager = new PlayerManager();
        return pManager;
    }
}
