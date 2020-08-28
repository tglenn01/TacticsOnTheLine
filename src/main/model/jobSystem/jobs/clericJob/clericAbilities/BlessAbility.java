package main.model.jobSystem.jobs.clericJob.clericAbilities;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.abilities.StatusEffectAbility;

public class BlessAbility extends StatusEffectAbility {
    public BlessAbility() {
        super("Bless", 4, 1, 1, 1, 10,
                "Heal a neighbouring ally");
    }

    @Override
    protected boolean resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        healUnit(receivingUnit, receivingUnitStatSheet, this.potency + activeUnitStatSheet.getMagic());
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
