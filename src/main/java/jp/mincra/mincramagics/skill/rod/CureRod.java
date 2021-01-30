package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.skill.MincraParticle;
import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CureRod {

    public void Cure(Player player, int level) {
        String id = "rod_cure_"+level;

        if (MincraMagics.getSkillManager().canUseSkill(player, id)) {

            MincraMagics.getSkillManager().useSkill(player,id);

            MincraParticle mincraParticle = new MincraParticle();
            mincraParticle.setRadius(2.0);
            mincraParticle.setParticle(Particle.VILLAGER_HAPPY);

            //メイン
            new BukkitRunnable() {
                @Override
                public void run() {
                    Player targetPlayer = Bukkit.getPlayer(player.getName());
                    List<Entity> entityList = targetPlayer.getNearbyEntities(3 * level, 5 * level, 3 * level);
                    entityList.add(targetPlayer);

                    int maxTargetAmount = level * 2; //人数制限

                    StringBuffer buffer = new StringBuffer("&#ec463a&f");

                    for (Entity entity : entityList) {
                        if (entity instanceof  Player && maxTargetAmount > 0) {
                            maxTargetAmount = maxTargetAmount - 1;

                            //メイン
                            ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.HEAL,1,1 * level));
                            ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,1,100 * level));

                            //装飾
                            Location targetLocation = targetPlayer.getLocation();
                            targetLocation.getWorld().playSound(targetLocation, Sound.ENTITY_VILLAGER_YES, (float) 0.3, 1);

                            targetLocation.getWorld().spawnParticle(Particle.HEART, targetLocation.add(0,1.7,0), 10 * level,0.42,0.42,0.42,1);
                            mincraParticle.drawMagicCircle(targetLocation,5,1,3,0.01,0.005);

                            //メッセージ
                            if (!entity.equals(targetPlayer)) {
                                buffer.append(targetPlayer.getName());
                                buffer.append("から癒しの杖lv1を効果を受けました。");

                                entity.sendMessage(ChatUtil.setColorCodes(buffer.toString()));
                            }
                        }
                    }

                    this.cancel();
                }
            }.runTaskLater(MincraMagics.getInstance(),40);


            //装飾
            Location casterLocation = player.getLocation();

            casterLocation.getWorld().playSound(casterLocation, Sound.BLOCK_PORTAL_TRAVEL, (float) 0.01, 2);

            mincraParticle.setRadius(2.4);
            mincraParticle.drawMagicCircle(casterLocation,5,1,3,0.01,0.005);



        }
    }
}
