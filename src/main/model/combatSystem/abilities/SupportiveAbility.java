package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.itemSystem.ResourceReplenishBonus;

public abstract class SupportiveAbility extends Ability {

    public SupportiveAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                             AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect,  abilityType, abilityDescription);
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

    protected int buffAttack(StatSheet receivingUnitStatSheet, int potency) {
        receivingUnitStatSheet.addStrength(potency);
        System.out.println("Attack is now buffed to " + receivingUnitStatSheet.getStrength());
        return potency;
    }

    protected int buffDefense(StatSheet receivingUnitStatSheet, int potency) {
        receivingUnitStatSheet.addArmour(potency);
        System.out.println("Defense is now buffed to " + receivingUnitStatSheet.getArmour());
        return potency;
    }

    protected int debuffAttack(StatSheet receivingUnitStatSheet, int potency) {
        if (receivingUnitStatSheet.getStrength() >= potency) {
            receivingUnitStatSheet.removeStrength(potency);
            System.out.println("Attack is now debuffed to " + receivingUnitStatSheet.getStrength());
            return potency;
        } else {
            int initialStrength = receivingUnitStatSheet.getStrength();
            receivingUnitStatSheet.removeStrength(initialStrength); // sets strength at 0
            System.out.println("Attack is now debuffed to " + receivingUnitStatSheet.getStrength());
            return initialStrength;
        }
    }

    protected int debuffDefense(StatSheet receivingUnitStatSheet, int potency) {
        if (receivingUnitStatSheet.getArmour() >= potency) {
            receivingUnitStatSheet.removeArmour(potency);
            System.out.println("Armour is now debuffed to " + receivingUnitStatSheet.getArmour());
            return potency;
        } else {
            int initialArmour = receivingUnitStatSheet.getArmour();
            receivingUnitStatSheet.removeStrength(initialArmour); // sets armour at 0
            System.out.println("Armour is now debuffed to " + receivingUnitStatSheet.getArmour());
            return initialArmour;
        }
    }
}
