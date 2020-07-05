package main.model.combatSystem.statusEffects;

import main.model.combatSystem.Ability;
import main.model.combatSystem.PermanentStatusEffect;

public class Invulnerable extends PermanentStatusEffect {

    // remove a hit
    public Invulnerable(Ability.AbilityType abilityType, int uses) {
        super(abilityType, uses);
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "INVUL";
    }

    @Override
    protected void setIcon() {
        //
    }
}
