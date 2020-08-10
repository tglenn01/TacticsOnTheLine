package main.model.jobSystem.jobs.clericJob.clericAbilities;

import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseBuff;

import java.util.List;

public class ProtectAbility extends StatusEffectAbility {
    public ProtectAbility() {
        super("Protect", 8, 1, 2, 2,  4,
                "Buff a neighbouring allies defense");
    }

    @Override
    protected void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseBuff(receivingUnit, this.potency, this.duration));
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
        return true;
    }

    @Override
    public List<BoardSpace> getTargetedBoardSpaces(CharacterUnit activeUnit) {
        return getNormalTargetPattern(activeUnit.getBoardSpace(), this.range, this);
    }
}
