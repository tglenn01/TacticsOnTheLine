package main.model.combatSystem.statusEffects;

import main.model.combatSystem.Ability;
import main.model.combatSystem.StatusEffect;

public class AttackBuff extends StatusEffect {

    public AttackBuff(Ability.AbilityType abilityType, int amountChanged, int duration) {
        super(abilityType, amountChanged, duration);

    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "ATK_UP";
    }

    @Override
    protected void setIcon() {

    }
}
