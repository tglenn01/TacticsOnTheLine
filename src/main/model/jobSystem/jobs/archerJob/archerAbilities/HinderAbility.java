package main.model.jobSystem.jobs.archerJob.archerAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseDebuff;

public class HinderAbility extends StatusEffectAbility {
    public HinderAbility() {
        super("Hinder", 7, 6, 1, 2, 4,
                "Fire a trap to lower an enemies defense from afar");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new DefenseDebuff(receivingUnit, this.potency, this.duration);
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
