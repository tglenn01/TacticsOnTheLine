package main.model.jobSystem.jobs.thiefJob.thiefAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.Blind;

public class BlindAbility extends StatusEffectAbility {
    public BlindAbility() {
        super("Blind", 10, 1, 2, 3, 15,
                "Blind enemies lowering their attack potency");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        new Blind(receivingUnit, this.potency, this.duration);
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
