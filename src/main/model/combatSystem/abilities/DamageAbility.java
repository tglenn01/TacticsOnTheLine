package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;

public abstract class DamageAbility extends Ability {
    protected int damage;
    protected double accuracy;
    protected CharacterUnit activeUnit;
    protected CharacterUnit receivingUnit;
    protected StatSheet activeUnitStatSheet;
    protected StatSheet receivingUnitStatSheet;

    public DamageAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                         AbilityType abilityType, int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
        this.damage = damage;
        this.accuracy = accuracy;
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws
            AttackMissedException, UnitIsDeadException {
        this.activeUnit = activeUnit;
        this.receivingUnit = receivingUnit;
        activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        receivingUnitStatSheet = this.receivingUnit.getCharacterStatSheet();
        checkIfAbilityHit();
        calculateDamageDone();
    }

    private void calculateDamageDone() throws UnitIsDeadException {
        int defenderHealth = receivingUnitStatSheet.getHealth();
        int damage =  calculateDamage();
        defenderHealth = defenderHealth - damage;
        if (defenderHealth < 0) defenderHealth = 0;
        System.out.println(activeUnit.getCharacterName() + " dealt " + damage + " to " +
                receivingUnit.getCharacterName() + ", " +
                receivingUnit.getCharacterName() + " is now at " + defenderHealth + "!");
        if (defenderHealth <= 0) {
            receivingUnitStatSheet.setHealth(0);
            receivingUnit.setAlive(false);
            throw new UnitIsDeadException(receivingUnit);
        }
        receivingUnitStatSheet.setHealth(defenderHealth);
    }

    private void checkIfAbilityHit() throws AttackMissedException {
        double activeUnitChanceToHit = this.accuracy + (activeUnitStatSheet.getDexterity() / 100.00);
        double reciveingUnitChanceToDodge = Math.random() + receivingUnitStatSheet.getDexterity() / 100.00;
        if (reciveingUnitChanceToDodge > activeUnitChanceToHit) {
            throw new AttackMissedException(receivingUnit);
        }
    }

    protected abstract int calculateDamage();
}
