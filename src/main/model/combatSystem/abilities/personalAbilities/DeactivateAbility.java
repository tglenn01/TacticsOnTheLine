package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.combatSystem.abilities.SupportiveAbility;
import main.model.itemSystem.ResourceReplenishBonus;

public class DeactivateAbility extends SupportiveAbility {
    private CharacterUnit activeUnit;

    public DeactivateAbility(String abilityName, int manaCost, int range, int areaOfEffect, AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        this.activeUnit = activeUnit;
        healUnit(receivingUnit, receivingUnit.getCharacterStatSheet(), activeUnit.getCharacterStatSheet());
        activeUnit.setMovementToken(false);
        activeUnit.setActionToken(0);
    }

    @Override
    protected int getHealAmount(ResourceReplenishBonus bonus) {
        return activeUnit.getCharacterStatSheet().getMaxHealth();
    }

    @Override
    protected int getManaGainAmount(ResourceReplenishBonus bonus) {
        return 0;
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }
}
