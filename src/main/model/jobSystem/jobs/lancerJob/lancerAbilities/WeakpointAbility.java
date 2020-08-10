package main.model.jobSystem.jobs.lancerJob.lancerAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.PhysicalAbility;

import java.util.List;

public class WeakpointAbility extends PhysicalAbility {
    public WeakpointAbility() {
        super("Weakpoint", 6, 1, 1, 16, .80,
                "Strike an Enemies weak point with the blunt of your spear");
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
