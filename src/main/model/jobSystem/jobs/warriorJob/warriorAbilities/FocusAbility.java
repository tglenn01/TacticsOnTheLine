package main.model.jobSystem.jobs.warriorJob.warriorAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.AttackBuff;

public class FocusAbility extends StatusEffectAbility {
    public FocusAbility() {
        super("Focus", 4, 1, 2, 1, 4,
                "Buff the attack of a neighbouring ally");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new AttackBuff(receivingUnit, this.potency, this.duration);
        return true;
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

}
