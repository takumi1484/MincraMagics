package jp.mincra.mincramagics.skill.rod;

import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.event.player.PlayerUseMagicRodEvent;
import org.bukkit.entity.Player;

public class BarrierRod implements PlayerUseMagicRodEvent {

    @Override
    public void onPlayerUseMagicRod(Player player, String mcr_id) {
        if (mcr_id.contains("rod_exp")) {

            switch (Integer.parseInt(mcr_id.substring(mcr_id.length() - 1))) {
                case 1:
                    BarrierOne(player, mcr_id);
                    break;
                    /*
                case 2:
                    TemplateTwo(player, mcr_id);
                    break;

                case 3:
                    TemplateThree(player, mcr_id);
                    */
            }
        }
    }

    public void BarrierOne(Player player, String mcr_id) {

        if (MincraMagics.getSkillManager().canUseSkill(player, mcr_id)) {

            MincraMagics.getSkillManager().useSkill(player, mcr_id);

            //メイン


            //装飾


        }
    }
}
