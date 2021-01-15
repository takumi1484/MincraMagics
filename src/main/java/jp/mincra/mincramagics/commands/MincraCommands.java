package jp.mincra.mincramagics.commands;

import jp.mincra.mincramagics.MincraMagics;
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
            MincraMagics.getPlayerManager().addPlayerMP_value(caster.getUniqueId(), 10);
        }
        return false;
    }
}
