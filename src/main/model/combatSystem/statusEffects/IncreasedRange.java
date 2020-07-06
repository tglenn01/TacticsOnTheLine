package main.model.combatSystem.statusEffects;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.abilities.MagicAbility;

public class IncreasedRange extends DecayingStatusEffect {


    public IncreasedRange(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }

    @Override
    protected void setAbilityType() {
        //
    }

    @Override
    protected void setCondensedName() {
        //
    }

    @Override
    protected void setIcon() {
        //
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        for (Ability ability : receivingUnit.getAbilityList()) {
            if (ability.getClass() == MagicAbility.class) {
                ability.setRange(ability.getRange() + potency); // oldRange + increasedRange
            }
        }
        this.amountChanged = potency;
    }

    @Override
    protected void removeStatusEffect(CharacterUnit receivingUnit) {
        for (Ability ability : receivingUnit.getAbilityList()) {
            if (ability.getClass() == MagicAbility.class) {
                ability.setRange(ability.getRange() - amountChanged);
            }
        }
    }
}
