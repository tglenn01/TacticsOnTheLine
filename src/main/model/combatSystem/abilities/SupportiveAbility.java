package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.itemSystem.ResourceReplenishBonus;

public abstract class SupportiveAbility extends Ability {
    protected static int STRENGTH_CONSTANT = 3;
    protected static int DEFENSE_CONSTANT = 2;
    protected int duration;

    public SupportiveAbility(String abilityName, int manaCost, int range, int areaOfEffect, int duration,
                               AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
        this.duration = duration;
    }

    public SupportiveAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                             AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public abstract void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit)
            throws AttackMissedException, UnitIsDeadException;

    protected void healUnit(CharacterUnit receivingUnit, StatSheet receivingUnitStatSheet, ResourceReplenishBonus healingEffect) {
        int initialHealth = receivingUnitStatSheet.getHealth();
        int healAmount = getHealAmount(healingEffect);
        int newHealth = initialHealth + healAmount;
        if (newHealth > receivingUnitStatSheet.getMaxHealth()) {
            newHealth = receivingUnitStatSheet.getMaxHealth();
            healAmount = newHealth - initialHealth;
        }
        receivingUnitStatSheet.setHealth(newHealth);
        System.out.println(receivingUnit.getCharacterName() + " was healed " + healAmount);
        System.out.println(receivingUnit.getCharacterName() + " health is now " +
                receivingUnitStatSheet.getHealth());
    }

    protected abstract int getHealAmount(ResourceReplenishBonus bonus);

    protected void gainMana(CharacterUnit receivingUnit, StatSheet receivingUnitStatSheet, ResourceReplenishBonus healingEffect) {
        int initialMana = receivingUnitStatSheet.getMana();
        int manaGainAmount = getManaGainAmount(healingEffect);
        int newMana = initialMana + manaGainAmount;
        if (newMana > receivingUnitStatSheet.getMaxMana()) {
            newMana = receivingUnitStatSheet.getMaxMana();
            manaGainAmount = newMana - initialMana;
        }
        receivingUnitStatSheet.setHealth(newMana);
        System.out.println(receivingUnit.getCharacterName() + " has gained " + manaGainAmount + " mana");
        System.out.println(receivingUnit.getCharacterName() + " mana is now " +
                receivingUnitStatSheet.getMana());
    }

    protected abstract int getManaGainAmount(ResourceReplenishBonus bonus);

    protected void buffAttack(StatSheet receivingUnitStatSheet) {
        int initialStrength = receivingUnitStatSheet.getStrength();
        receivingUnitStatSheet.setStrength(initialStrength + STRENGTH_CONSTANT);
        System.out.println("Attack is now buffed to " + receivingUnitStatSheet.getStrength());
    }

    protected void buffDefense(StatSheet receivingUnitStatSheet) {
        int initialDefense = receivingUnitStatSheet.getArmour();
        receivingUnitStatSheet.setArmour(initialDefense + DEFENSE_CONSTANT);
        System.out.println("Defense is now buffed to " + receivingUnitStatSheet.getArmour());
    }

    protected void debuffAttack(StatSheet receivingUnitStatSheet) {
        int initialStrength = receivingUnitStatSheet.getStrength();
        if (initialStrength - STRENGTH_CONSTANT > 0) {
            receivingUnitStatSheet.setStrength(initialStrength - STRENGTH_CONSTANT);
        } else {
            receivingUnitStatSheet.setStrength(0);
        }
        System.out.println("Attack is now debuffed to " + receivingUnitStatSheet.getStrength());
    }

    protected void debuffDefense(StatSheet receivingUnitStatSheet) {
        int initialDefense = receivingUnitStatSheet.getArmour();
        if (initialDefense - DEFENSE_CONSTANT > 0) {
            receivingUnitStatSheet.setArmour(initialDefense - DEFENSE_CONSTANT);
        } else {
            receivingUnitStatSheet.setArmour(0);
        }
        System.out.println("Defense is now debuffed to " + receivingUnitStatSheet.getArmour());
    }

    public int getDuration() {
        return duration;
    }
}
