package main.model.jobSystem.jobs.clericJob.clericAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.MagicAbility;

import java.util.List;

public class HolyAbility extends MagicAbility {

    public HolyAbility() {
        super("Holy", 10, 3, 1, 20, .80,
                "Deal heavy light damage to a nearby enemy");
    }


    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }

    @Override
    public List<BoardSpace> getTargetedBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range, this);
    }
}
