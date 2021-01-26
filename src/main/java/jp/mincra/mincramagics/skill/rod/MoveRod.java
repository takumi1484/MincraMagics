package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MagicCircle;
import jp.mincra.mincramagics.util.ChatUtil;
import jp.mincra.mincramagics.util.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
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

                //メイン
                float yaw = player.getLocation().getYaw();
                Vector vec = new Vector(-1 * Math.sin(yaw * Math.PI / 180.0) * 1, -0.5F, Math.cos(yaw * Math.PI / 180.0) * 1);
                player.setVelocity(vec);

                //装飾
                player.playSound(loc, Sound.ENTITY_WITHER_SHOOT, 0.2F, 1F);

                MagicCircle magicCircle = new MagicCircle();
                magicCircle.setType(1);
                magicCircle.setRadius(2);
                magicCircle.setParticle(0, Particle.FIREWORKS_SPARK);
                vec.setY(vec.getY()+1d);
                ParticleUtil.drawMagicCircle(magicCircle, player, loc.add(vec));
            } else {
                player.sendMessage(ChatUtil.translateHexColorCodes("&c高すぎるため使えません！"));
            }
        }
    }
}
