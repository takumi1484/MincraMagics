package jp.mincra.mincramagics.command;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.entity.player.PlayerManager;
import jp.mincra.mincramagics.util.MincraChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MincraCommands implements CommandExecutor {

    private JavaPlugin plugin;
    public MincraCommands(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player caster;
        if (sender instanceof Player) {
            caster = (Player) sender;
            caster.sendMessage(MincraChatUtil.makeDebugMessage(args[0]));
            PlayerManager playerManager = MincraMagics.getPlayerManager();
            playerManager.addPlayerMP_value(caster.getUniqueId(), 20);
            playerManager.setPlayerCooltime(caster.getUniqueId(), 10);
        }
        return false;
    }
}
