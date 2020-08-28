package main.model.jobSystem.jobs.bardJob.bardAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseBuff;

public class SerenadeAbility extends StatusEffectAbility {
    public SerenadeAbility() {
        super("Serenade", 10, 4, 2, 3, 2,
                "Serenade your allies, buffing their defense");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseBuff(receivingUnit, this.potency, this.duration));
        return true;
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
}
