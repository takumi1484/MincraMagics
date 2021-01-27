package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.util.ChatUtil;
import jp.mincra.mincramagics.skill.MincraParticle;
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

                final double speed = 1.0;

                //メイン
                float yaw = player.getLocation().getYaw();
                Vector vec = new Vector(-speed * Math.sin(yaw * Math.PI / 180.0) * 1, -0.5 * speed, Math.cos(yaw * Math.PI / 180.0) * speed);
                player.setVelocity(vec);

                //装飾
                player.playSound(loc, Sound.ENTITY_WITHER_SHOOT, 0.2F, 1F);

                MincraParticle mincraParticle = new MincraParticle();
                mincraParticle.setRadius(2);
                mincraParticle.setParticle(Particle.SPELL_INSTANT);
                //距離調整
                vec.multiply(2.2 * speed);
                vec.setY(vec.getY()+1d);

                mincraParticle.drawMagicCircle(loc.add(vec), 5, 1);

            } else {
                player.sendMessage(ChatUtil.translateHexColorCodes("&c高すぎるため使えません！"));
            }
        }
    }
}
