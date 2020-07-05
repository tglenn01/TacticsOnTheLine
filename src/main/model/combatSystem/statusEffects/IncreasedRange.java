package main.model.combatSystem.statusEffects;

import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.DecayingStatusEffect;

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
        //
    }
}
