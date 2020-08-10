package main.model.jobSystem.jobs.warriorJob.warriorAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.PhysicalAbility;

import java.util.List;

public class StoneAbility extends PhysicalAbility {
    public StoneAbility() {
        super("Stone", 1, 4, 1, 8, .60,
                "A low chance to damage an enemy from afar");
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
