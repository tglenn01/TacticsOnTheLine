package main.model.jobSystem.jobs.thiefJob.thiefAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.PhysicalAbility;

import java.util.List;

public class DaggerThrowAbility extends PhysicalAbility {
    public DaggerThrowAbility() {
        super("Dagger Throw", 4, 8, 1, 10, .70,
                "Throw a dagger at an enemy from afar");
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
