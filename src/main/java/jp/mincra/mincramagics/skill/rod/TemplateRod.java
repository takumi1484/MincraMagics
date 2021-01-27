package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import org.bukkit.entity.Player;

public class TemplateRod {

    public void TemplateOne(Player player) {
        String id = "rod_template_1";

        if (MincraMagics.getSkillManager().canUseSkill(player, id)) {

            MincraMagics.getSkillManager().useSkill(player,id);

        }
    }
}
