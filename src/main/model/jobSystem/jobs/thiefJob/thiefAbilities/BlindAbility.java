package main.model.jobSystem.jobs.thiefJob.thiefAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.StatusEffectAbility;

import java.util.List;

public class BlindAbility extends StatusEffectAbility {
    public BlindAbility() {
        super("Blind", 10, 1, 1, 3,
                Ability.AbilityType.ATTACK_DEBUFF, 7, "Blind enemies lowering their attack potency");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            resolveEffect(activeUnit, boardSpace.getOccupyingUnit());
        }
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
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range);
    }
}
