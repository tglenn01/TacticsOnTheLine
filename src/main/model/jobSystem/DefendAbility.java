package main.model.jobSystem;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.StatusEffectAbility;
import main.model.combatSystem.statusEffects.DefenseBuff;

public class DefendAbility extends StatusEffectAbility {
    public DefendAbility() {
        super("Defend", 0, 0, 1, 1, 3,
                "Strengthen one's own defenses");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseBuff(receivingUnit, this.potency, this.duration));
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
        return true;
    }
}
