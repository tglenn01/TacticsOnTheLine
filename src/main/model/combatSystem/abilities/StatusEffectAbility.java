package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;

public class StatusEffectAbility extends Ability {

    public enum StatusType {HEAL, ATTACK_BUFF, DEFENSE_BUFF, ATTACK_DEBUFF, DEFENSE_DEBUFF}
    private static int HEAL_CONSTANT = 10;
    private static int STRENGTH_CONSTANT = 3;
    private static int DEFENSE_CONSTANT = 2;
    private StatusType statusType;

    public StatusEffectAbility(String abilityName, int manaCost, int range, int areaOfEffect, StatusType statusType) {
        super(abilityName, manaCost, range, areaOfEffect);
        this.statusType = statusType;
    }


    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit)
            throws OutOfManaException, AttackMissedException, UnitIsDeadException {
        hasEnoughMana(activeUnit);
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (this.statusType == StatusType.HEAL) {
            healUnit(activeUnitStatSheet, receivingUnitStatSheet);
        } if (this.statusType == StatusType.ATTACK_BUFF) {
            buffAttack(receivingUnitStatSheet);
        } if (this.statusType == StatusType.DEFENSE_BUFF) {
            buffDefense(receivingUnitStatSheet);
        } if (this.statusType == StatusType.ATTACK_DEBUFF) {
            debuffAttack(receivingUnitStatSheet);
        } if (this.statusType == StatusType.DEFENSE_DEBUFF) {
            debuffDefense(receivingUnitStatSheet);
        }
    }

    private void healUnit(StatSheet activeUnitStatSheet, StatSheet receivingUnitStatSheet) {
        int initialHeal = receivingUnitStatSheet.getHealth();
        int healAmount = activeUnitStatSheet.getMagic() + HEAL_CONSTANT;
        receivingUnitStatSheet.setHealth(initialHeal + healAmount);
    }

    private void buffAttack(StatSheet receivingUnitStatSheet) {
        int initialStrength = receivingUnitStatSheet.getStrength();
        receivingUnitStatSheet.setStrength(initialStrength + STRENGTH_CONSTANT);
    }

    private void buffDefense(StatSheet receivingUnitStatSheet) {
        int initialDefense = receivingUnitStatSheet.getArmour();
        receivingUnitStatSheet.setArmour(initialDefense + 2);
    }

    private void debuffAttack(StatSheet receivingUnitStatSheet) {
        int initialStrength = receivingUnitStatSheet.getStrength();
        if (initialStrength - STRENGTH_CONSTANT > 0) {
            receivingUnitStatSheet.setStrength(initialStrength - STRENGTH_CONSTANT);
        } else {
            receivingUnitStatSheet.setStrength(0);
        }
    }

    private void debuffDefense(StatSheet receivingUnitStatSheet) {
        int initialDefense = receivingUnitStatSheet.getArmour();
        if (initialDefense - DEFENSE_CONSTANT > 0) {
            receivingUnitStatSheet.setArmour(initialDefense - DEFENSE_CONSTANT);
        } else {
            receivingUnitStatSheet.setArmour(0);
        }
    }








}
