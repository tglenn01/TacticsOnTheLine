package main.model.jobSystem.jobs.lancerJob.lancerAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseDebuff;

public class OverwhelmAbility extends StatusEffectAbility {
    public OverwhelmAbility() {
        super("Overwhelm", 20, 2, 2, 3, 2,
                "Scare all neighbouring enemies, weakening their defense");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseDebuff(receivingUnit, this.potency, this.duration));
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
