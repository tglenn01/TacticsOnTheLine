package main.model.combatSystem.abilities;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.graphics.sceneElements.images.HealthBar;

public abstract class SupportiveAbility extends Ability {

    public SupportiveAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                             String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityDescription);
    }

    public static void healUnit(CharacterUnit receivingUnit, StatSheet receivingUnitStatSheet, int healAmount) {
        int initialHealth = receivingUnitStatSheet.getHealth();
        int maxHealth = receivingUnitStatSheet.getMaxHealth();
        int newHealth = initialHealth + healAmount;

        if (newHealth > maxHealth) {
            newHealth = maxHealth;
            healAmount = newHealth - initialHealth;
        }

        HealthBar healthBar = new HealthBar(receivingUnit, maxHealth, initialHealth, newHealth);
        healthBar.showAndWait();

        effectPopupAnimation(receivingUnit, Integer.toString(healAmount), "healingNode");



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

    @Override
    public int getHitChance(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        return 100;
    }
}
