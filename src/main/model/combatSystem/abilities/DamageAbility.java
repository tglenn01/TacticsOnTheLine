package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;

public abstract class DamageAbility extends Ability {
    protected int damage;
    protected double accuracy;
    protected CharacterUnit activeUnit;
    protected CharacterUnit recivingUnit;
    protected StatSheet activeUnitStatSheet;
    protected StatSheet recivingUnitStatSheet;

    public DamageAbility(String abilityName, int manaCost, int range, int areaOfEffect, int damage, double accuracy) {
        super(abilityName, manaCost, range, areaOfEffect);
        this.damage = damage;
        this.accuracy = accuracy;
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit reciveingUnit) throws OutOfManaException,
            AttackMissedException, UnitIsDeadException {
        this.activeUnit = activeUnit;
        this.recivingUnit = reciveingUnit;
        activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        recivingUnitStatSheet = recivingUnit.getCharacterStatSheet();
        hasEnoughMana();
        checkIfAbilityHit();
        calculateDamageDone();
    }

    private void hasEnoughMana() throws OutOfManaException {
        if (recivingUnitStatSheet.getMana() >= manaCost) {
            recivingUnitStatSheet.setMana(recivingUnitStatSheet.getMana() - manaCost);
        } else {
            throw new OutOfManaException();
        }
    }

    private void calculateDamageDone() throws UnitIsDeadException {
        int defenderHealth = recivingUnitStatSheet.getHealth();
        int damage =  calculateDamage();
        defenderHealth = defenderHealth - damage;
        if (defenderHealth <= 0) {
            recivingUnitStatSheet.setHealth(0);
            recivingUnit.setAlive(false);
            throw new UnitIsDeadException(recivingUnit);
        }
        System.out.println(activeUnit.getCharacterName() + " dealt " + damage + " to " +
                recivingUnit.getCharacterName() + ", " +
                recivingUnit.getCharacterName() + " is now at " + defenderHealth + "!");
        recivingUnitStatSheet.setHealth(defenderHealth);
    }

    private void checkIfAbilityHit() throws AttackMissedException {
        double activeUnitChanceToHit = this.accuracy + (activeUnitStatSheet.getDexterity() / 100.00);
        double reciveingUnitChanceToDodge = Math.random() + recivingUnitStatSheet.getDexterity() / 100.00;
        if (reciveingUnitChanceToDodge > activeUnitChanceToHit) {
            throw new AttackMissedException();
        }
    }

    protected abstract int calculateDamage();
}
