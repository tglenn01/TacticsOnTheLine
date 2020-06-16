package main.model.combatSystem.statusEffects;

import main.model.combatSystem.Ability;
import main.model.combatSystem.StatusEffect;

public class AttackDebuff extends StatusEffect {

    public AttackDebuff(Ability.AbilityType abilityType, int amountChanged, int duration) {
        super(abilityType, amountChanged, duration);

    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "ATK_DN";
    }

    @Override
    protected void setIcon() {

    }
}
