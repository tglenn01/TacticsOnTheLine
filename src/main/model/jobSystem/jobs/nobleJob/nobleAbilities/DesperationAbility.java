package main.model.jobSystem.jobs.nobleJob.nobleAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.MagicAbility;

import java.util.List;

public class DesperationAbility extends MagicAbility {
    public DesperationAbility() {
        super("Desperation", 10, 1, 2, 30, .50,
                "Consume a lot of mana for a low chance to deal a lot " +
                        "of magical damage to multiple neighbouring enemies");
    }

    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return true;
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
