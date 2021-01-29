package jp.mincra.mincramagics.command;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MincraTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){

        if (args.length == 1) {
            return Arrays.asList("give","reload");

        }

        if (args.length > 1) {
            switch (args[0]) {
                case "give":
                    return give(args);

            }
        }

        return null;
    }

    private List<String> give(String[] args) {
        List<String> completions = new ArrayList<>();

        //give Harineko0
        if (args.length == 3) {
            for (String key : MincraMagics.getItemManager().getItemStackMap().keySet()) {
                completions.add(key);
            }
            return completions;
        }

        return null;
    }

}
