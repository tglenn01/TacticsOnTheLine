package main.model.jobSystem.jobs.thiefJob.thiefAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.Blind;

import java.util.List;

public class BlindAbility extends StatusEffectAbility {
    public BlindAbility() {
        super("Blind", 10, 1, 1, 3, 15,
                "Blind enemies lowering their attack potency");
    }

    @Override
    protected void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new Blind(receivingUnit, this.potency, this.duration));
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
