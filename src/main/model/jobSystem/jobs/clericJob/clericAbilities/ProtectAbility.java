package main.model.jobSystem.jobs.clericJob.clericAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseBuff;

public class ProtectAbility extends StatusEffectAbility {
    public ProtectAbility() {
        super("Protect", 8, 1, 2, 2,  4,
                "Buff a neighbouring allies defense");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new DefenseBuff(receivingUnit, this.potency, this.duration);
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
