package main.model.combatSystem.abilities;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;

public abstract class SupportiveAbility extends Ability {

    public SupportiveAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                             String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityDescription);
    }

    public static void healUnit(CharacterUnit receivingUnit, StatSheet receivingUnitStatSheet, int healAmount) {
        int initialHealth = receivingUnitStatSheet.getHealth();
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

    public static void gainMana(CharacterUnit receivingUnit, StatSheet receivingUnitStatSheet, int manaGainAmount) {
        int initialMana = receivingUnitStatSheet.getMana();
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
}
