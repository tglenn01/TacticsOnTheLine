package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;

public class StatusEffectAbility extends Ability {
    private static int HEAL_CONSTANT = 10;
    private static int STRENGTH_CONSTANT = 3;
    private static int DEFENSE_CONSTANT = 2;

    public StatusEffectAbility(String abilityName, int manaCost, int range, int areaOfEffect, AbilityType abilityType,
                               String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }


    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit)
            throws AttackMissedException, UnitIsDeadException {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (this.abilityType == AbilityType.HEAL) {
            healUnit(activeUnitStatSheet, receivingUnitStatSheet);
            System.out.println(receivingUnit.getCharacterName() + " health is now " +
                    receivingUnitStatSheet.getHealth());
        } if (this.abilityType == AbilityType.ATTACK_BUFF) {
            buffAttack(receivingUnitStatSheet);
        } if (this.abilityType == AbilityType.DEFENSE_BUFF) {
            buffDefense(receivingUnitStatSheet);
        } if (this.abilityType == AbilityType.ATTACK_DEBUFF) {
            debuffAttack(receivingUnitStatSheet);
        } if (this.abilityType == AbilityType.DEFENSE_DEBUFF) {
            debuffDefense(receivingUnitStatSheet);
        }
    }

    private void healUnit(StatSheet activeUnitStatSheet, StatSheet receivingUnitStatSheet) {
        int initialHeal = receivingUnitStatSheet.getHealth();
        int healAmount = activeUnitStatSheet.getMagic() + HEAL_CONSTANT;
        receivingUnitStatSheet.setHealth(initialHeal + healAmount);
        System.out.println("It was healed " + healAmount);
    }

    private void buffAttack(StatSheet receivingUnitStatSheet) {
        int initialStrength = receivingUnitStatSheet.getStrength();
        receivingUnitStatSheet.setStrength(initialStrength + STRENGTH_CONSTANT);
        System.out.println("Attack is now buffed!");
    }

    private void buffDefense(StatSheet receivingUnitStatSheet) {
        int initialDefense = receivingUnitStatSheet.getArmour();
        receivingUnitStatSheet.setArmour(initialDefense + DEFENSE_CONSTANT);
        System.out.println("Defense is now buffed!");
    }

    private void debuffAttack(StatSheet receivingUnitStatSheet) {
        int initialStrength = receivingUnitStatSheet.getStrength();
        if (initialStrength - STRENGTH_CONSTANT > 0) {
            receivingUnitStatSheet.setStrength(initialStrength - STRENGTH_CONSTANT);
        } else {
            receivingUnitStatSheet.setStrength(0);
        }
        System.out.println("Attack is now debuffed.");
    }

    private void debuffDefense(StatSheet receivingUnitStatSheet) {
        int initialDefense = receivingUnitStatSheet.getArmour();
        if (initialDefense - DEFENSE_CONSTANT > 0) {
            receivingUnitStatSheet.setArmour(initialDefense - DEFENSE_CONSTANT);
        } else {
            receivingUnitStatSheet.setArmour(0);
        }
        System.out.println("Defense is now debuffed");
    }
}
