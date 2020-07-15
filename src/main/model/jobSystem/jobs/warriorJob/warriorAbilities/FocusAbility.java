package main.model.jobSystem.jobs.warriorJob.warriorAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.StatusEffectAbility;

import java.util.List;

public class FocusAbility extends StatusEffectAbility {
    public FocusAbility() {
        super("Focus", 4, 1, 1, 1,
                Ability.AbilityType.ATTACK_BUFF, 4, "Buff the attack of a neighbouring ally");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces) {
        for (BoardSpace boardSpace : targetedBoardSpaces) {
            resolveEffect(activeUnit, boardSpace.getOccupyingUnit());
        }
    }

    @Override
    protected boolean targetsSelf() {
        return true;
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
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getSelfBoardSpace(activeUnit);
    }
}
