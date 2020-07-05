package main.model.combatSystem.abilities.personalAbilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;

public class Deactivate extends Ability {

    public Deactivate(String abilityName, int manaCost, int range, int areaOfEffect, AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        StatSheet characterStatSheet = activeUnit.getCharacterStatSheet();
        characterStatSheet.setHealth(characterStatSheet.getMaxHealth());
        activeUnit.setMovementToken(false);
        activeUnit.setActionToken(0);
    }

    @Override
    public boolean targetsAlly() {
        return false;
    }
}