package jp.mincra.mincramagics;

import jp.mincra.mincramagics.util.PropertyUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

public final class MincraMagics extends JavaPlugin {

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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
