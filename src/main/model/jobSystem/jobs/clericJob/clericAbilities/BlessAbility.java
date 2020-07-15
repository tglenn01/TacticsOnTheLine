package main.model.jobSystem.jobs.clericJob.clericAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.abilities.StatusEffectAbility;

import java.util.List;

public class BlessAbility extends StatusEffectAbility {
    public BlessAbility() {
        super("Bless", 4, 1, 1, 1,
                Ability.AbilityType.HEAL, 6, "Heal a neighbouring ally");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> chosenBoardSpaces) throws AttackMissedException, UnitIsDeadException {
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
    protected List<BoardSpace> getBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range);
    }
}
