package main.model.jobSystem.jobs.bardJob.bardAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.AttackBuff;

public class InspireAbility extends StatusEffectAbility {
    public InspireAbility() {
        super("Inspire", 10, 4, 2, 3,  2,
                "Inspire your allies, boosting their attack");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new AttackBuff(receivingUnit, this.potency, this.duration);
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
