package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.itemSystem.ResourceReplenishBonus;

import java.util.List;

public abstract class SupportiveAbility extends Ability {

    public SupportiveAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                             AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public abstract void takeAction(CharacterUnit activeUnit, List<BoardSpace> targetedBoardSpaces)
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
}
