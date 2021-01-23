package jp.mincra.mincramagics.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    private final static Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");
    private final static char COLOR_CHAR = ChatColor.COLOR_CHAR;

    /**
     * カラーコードを翻訳する。&修飾子はそのまま使える。
     * @param message usage: &#FF0000&fテキスト
     */
    public static String translateHexColorCodes(String message) {
        //Sourced from this post by imDaniX: https://github.com/SpigotMC/BungeeCord/pull/2883#issuecomment-653955600
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return ChatColor.translateAlternateColorCodes('&',matcher.appendTail(buffer).toString());
    }

    /**
     * @param msg 本文
     * @return [MincraMagics] 付きのメッセージ
     */
    public static String debug(String msg) {
        String prefix = "&#3ebef5&f[&#3de8fe&fMincraMagics&#3ebef5&f] &r";
        StringBuilder buf = new StringBuilder();
        buf.append(prefix);
        buf.append(msg);

        return translateHexColorCodes(buf.toString());
    }

    /**
     * コンソールにログ出力
     * @param msg 本文
     */
    public static void sendConsoleMessage(String msg) {
        String prefix = "[MincraMagics] ";
        StringBuilder buf = new StringBuilder();
        buf.append(prefix);
        buf.append(msg);

//        Bukkit.getServer().getConsoleSender().sendMessage(translateHexColorCodes(debug(msg)));
        Bukkit.getServer().getConsoleSender().sendMessage((buf.toString()));
    }
}