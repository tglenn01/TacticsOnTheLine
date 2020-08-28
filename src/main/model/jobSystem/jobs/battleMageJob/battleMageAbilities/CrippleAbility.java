package main.model.jobSystem.jobs.battleMageJob.battleMageAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.AttackDebuff;

public class CrippleAbility extends StatusEffectAbility {
    public CrippleAbility() {
        super("Cripple", 6, 2, 1, 3, 6,
                "Lower a nearby enemies attack");
    }


    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new AttackDebuff(receivingUnit, this.potency, this.duration));
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
        return false;
    }

}
