package main.model.jobSystem.jobs.nobleJob.nobleAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class BestowAbility extends StatusEffectAbility {

    public BestowAbility() {
        super("Bestow", 2, 1, 1, 1, 10,
                "Bestow mana upon a neighbouring ally");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        gainMana(receivingUnit, receivingUnitStatSheet, this.potency + activeUnitStatSheet.getMagic());
        return true;
    }

    @Override
    public boolean targetsAlly() {
        return true;
    }

    @Override
    protected boolean targetsSelf() {
        return false;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return false;
    }
}
