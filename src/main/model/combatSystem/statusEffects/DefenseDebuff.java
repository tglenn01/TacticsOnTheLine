package main.model.combatSystem.statusEffects;

import main.model.combatSystem.Ability;
import main.model.combatSystem.StatusEffect;

public class DefenseDebuff extends StatusEffect {

    public DefenseDebuff(Ability.AbilityType abilityType, int amountChanged, int duration) {
        super(abilityType, amountChanged, duration);

    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "DEF_DN";
    }

    @Override
    protected void setIcon() {

    }
}
