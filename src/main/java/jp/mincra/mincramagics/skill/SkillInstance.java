package jp.mincra.mincramagics.skill;

import jp.mincra.mincramagics.skill.rod.JumpRod;
import jp.mincra.mincramagics.skill.rod.MoveRod;

public class SkillInstance {

    private MoveRod moveRod = new MoveRod();

    private JumpRod jumpRod = new JumpRod();

    public MoveRod getMoveRod() {
        return moveRod;
    }

    public JumpRod getJumpRod() {
        return jumpRod;
    }
}
