package main.model.jobSystem.jobs.lancerJob.lancerAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseDebuff;

import java.util.List;

public class OverwhelmAbility extends StatusEffectAbility {
    public OverwhelmAbility() {
        super("Overwhelm", 20, 2, 2, 3, 2,
                "Scare all neighbouring enemies, weakening their defense");
    }

    @Override
    protected void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseDebuff(receivingUnit, this.potency, this.duration));
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
