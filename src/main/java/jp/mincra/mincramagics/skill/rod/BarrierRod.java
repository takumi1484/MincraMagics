package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import org.bukkit.entity.Player;

public class BarrierRod {

    public void BarrierOne(Player player) {
        String id = "rod_barrier_1";

        if (MincraMagics.getSkillManager().canUseSkill(player, id)) {

            MincraMagics.getSkillManager().useSkill(player,id);

            //メイン


            //装飾


        }
    }
}
