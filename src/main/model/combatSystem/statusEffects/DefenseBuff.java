package main.model.combatSystem.statusEffects;

import main.model.combatSystem.Ability;
import main.model.combatSystem.StatusEffect;

public class DefenseBuff extends StatusEffect {

    public DefenseBuff(Ability.AbilityType abilityType, int amountChanged, int duration) {
        super(abilityType, amountChanged, duration);

    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "DEF_UP";
    }

    @Override
    protected void setIcon() {

    }
}
