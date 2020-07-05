package main.model.combatSystem.statusEffects;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;

public class Root extends DecayingStatusEffect {


    public Root(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency, duration);
    }


    @Override
    protected void setAbilityType() {
        this.abilityType = Ability.AbilityType.ROOT;
    }

    @Override
    protected void setCondensedName() {
        this.condensedName = "ROOT";
    }

    @Override
    protected void setIcon() {
        //
    }

    @Override
    protected void applyStatusEffect(CharacterUnit receivingUnit, int potency) {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        this.amountChanged = receivingUnitStatSheet.getMovement();
        receivingUnitStatSheet.setMovement(0);
    }
}
