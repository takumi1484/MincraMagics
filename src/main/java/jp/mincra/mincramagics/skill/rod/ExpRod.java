package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.skill.MincraParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ExpRod {

    public void ExpOne(Player player) {
        String id = "rod_exp_1";

        if (MincraMagics.getSkillManager().canUseSkill(player, id)) {

            MincraMagics.getSkillManager().useSkill(player,id);

            //事前装飾
            Location location = player.getLocation();
            Vector vector = new Vector(0,1.5,0);
            location.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,location.add(vector),200,0.02,0.02,0.02,0.5);
            location.getWorld().playSound(location, Sound.BLOCK_PORTAL_TRAVEL, (float) 0.1,2);

            new BukkitRunnable() {

                @Override
                public void run() {

                    Player finalPlayer = Bukkit.getPlayer(player.getName());
                    Location location = finalPlayer.getLocation();

                    //メイン
                    if (finalPlayer.getLevel() < 5) {
                        finalPlayer.setLevel(5);
                        finalPlayer.setExp(0);

                    }

                    //装飾
                    location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                    MincraParticle mincraParticle = new MincraParticle();
                    mincraParticle.setRadius(2.4);
                    mincraParticle.setParticle(Particle.ENCHANTMENT_TABLE);

                    mincraParticle.drawMagicCircle(location.add(0,0.25,0), 6, 1, 4, 0.03, 0);

                    this.cancel();
                }
            }.runTaskLater(MincraMagics.getInstance(),80);
        }
    }

    public void ExpTwo(Player player) {
        String id = "rod_exp_2";

        if (MincraMagics.getSkillManager().canUseSkill(player, id)) {

            MincraMagics.getSkillManager().useSkill(player,id);

            //事前装飾
            Location location = player.getLocation();
            Vector vector = new Vector(0,1.5,0);
            location.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,location.add(vector),200,0.02,0.02,0.02,0.5);
            location.getWorld().playSound(location, Sound.BLOCK_PORTAL_TRAVEL, (float) 0.1,2);

            new BukkitRunnable() {

                @Override
                public void run() {

                    Player finalPlayer = Bukkit.getPlayer(player.getName());
                    Location location = finalPlayer.getLocation();

                    //メイン
                    if (finalPlayer.getLevel() < 10) {
                        finalPlayer.setLevel(10);
                        finalPlayer.setExp(0);

                    }

                    //装飾
                    location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                    MincraParticle mincraParticle = new MincraParticle();
                    mincraParticle.setRadius(2.4);
                    mincraParticle.setParticle(Particle.ENCHANTMENT_TABLE);

                    mincraParticle.drawMagicCircle(location.add(0,0.25,0), 6, 1, 10, 0.03, 0);

                    this.cancel();
                }
            }.runTaskLater(MincraMagics.getInstance(),80);
        }
    }


    public void ExpThree(Player player) {
        String id = "rod_exp_3";

        if (MincraMagics.getSkillManager().canUseSkill(player, id)) {

            MincraMagics.getSkillManager().useSkill(player,id);

            //メイン
            if (player.getLevel() < 5) {
                player.setLevel(5);
                player.setExp(0);

            }

            //装飾
            Location location = player.getLocation();
            location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP,1,1);

            MincraParticle mincraParticle = new MincraParticle();
            mincraParticle.setRadius(2.4);
            mincraParticle.setParticle(Particle.ENCHANTMENT_TABLE);

            mincraParticle.drawMagicCircle(location.add(0,0.25,0), 6, 1, 10, 0.03, 0);

        }
    }
}
