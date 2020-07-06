package main.model.combatSystem.statusEffects;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.PermanentStatusEffect;

public class Invulnerable extends PermanentStatusEffect {

    // the potency and the uses are the same in this status effect
    public Invulnerable(CharacterUnit receivingUnit, int potency) {
        super(receivingUnit, potency, potency);
    }

    @Override
    protected void setAbilityType() {
        this.abilityType = Ability.AbilityType.INVULNERABLE;
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "INVUL";
    }

    @Override
    protected void setIcon() {
        //
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {

    }

    @Override
    protected void removeStatusEffect(CharacterUnit receivingUnit) {

    }
}
