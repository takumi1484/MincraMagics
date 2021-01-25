package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MoveRod {

    public void MoveOne(Player player) {
        String id = "rod_move_1";

        if (MincraMagics.getSkillManager().canUseSkill(player,id)) {
            Location loc = player.getLocation();

            if(loc.getBlockY() < 170) {

                MincraMagics.getSkillManager().useSkill(player, id);

                player.playSound(loc, Sound.ENTITY_WITHER_SHOOT, 0.2F, 1F);

                float yaw = player.getLocation().getYaw();
                Vector vec = new Vector(-1 * Math.sin(yaw * Math.PI / 180.0) * 1, -0.5F, Math.cos(yaw * Math.PI / 180.0) * 1);
                player.setVelocity(vec);
            } else {
                player.sendMessage(ChatUtil.translateHexColorCodes("&c高すぎるため使えません！"));
            }
        }
    }
}
