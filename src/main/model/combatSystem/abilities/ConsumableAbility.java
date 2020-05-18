package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.itemSystem.ResourceReplenishBonus;
import main.ui.UserInput;

public class ConsumableAbility extends SupportiveAbility {

    public ConsumableAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                             AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit)
            throws AttackMissedException, UnitIsDeadException {
        Consumable consumable = getChosenItem();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (consumable.getAbilityType() == AbilityType.HEAL) {
            healUnit(receivingUnit, receivingUnitStatSheet, consumable);
        } if (consumable.getAbilityType() == AbilityType.MANA_GAIN) {
            gainMana(receivingUnit, receivingUnitStatSheet, consumable);
        } if (consumable.getAbilityType() == AbilityType.ATTACK_BUFF) {
            buffAttack(receivingUnitStatSheet);
            receivingUnit.addStatusEffect(consumable.getAbilityType(), consumable.getDuration());
        } if (consumable.getAbilityType() == AbilityType.DEFENSE_BUFF) {
            buffDefense(receivingUnitStatSheet);
            receivingUnit.addStatusEffect(consumable.getAbilityType(), consumable.getDuration());
        } if (consumable.getAbilityType() == AbilityType.ATTACK_DEBUFF) {
            debuffAttack(receivingUnitStatSheet);
            receivingUnit.addStatusEffect(consumable.getAbilityType(), consumable.getDuration());
        } if (consumable.getAbilityType() == AbilityType.DEFENSE_DEBUFF) {
            debuffDefense(receivingUnitStatSheet);
            receivingUnit.addStatusEffect(consumable.getAbilityType(), consumable.getDuration());
        }
        ConsumableItemInventory.getInstance().removeConsumableItem(consumable);
    }

    private Consumable getChosenItem() {
        listConsumableItems();
        UserInput input = new UserInput();
        String command = input.getInput();
        Consumable chosenConsumable = null;

        for (Consumable consumable : ConsumableItemInventory.getInstance()) {
            if (command.equals(consumable.getConsumableName())) {
                chosenConsumable = consumable;
                break; // if multiple of one item it will take the first one in the inventory
            }
        }

        if (chosenConsumable == null) {
            System.out.println("Not a valid item, please choose again");
            getChosenItem();
        }
        return chosenConsumable;
    }

    private void listConsumableItems() {
        System.out.println("Choose an item to use");
        for (Consumable consumable : ConsumableItemInventory.getInstance()) {
            System.out.println(consumable.getConsumableName());
        }
    }

    @Override
    protected int getHealAmount(ResourceReplenishBonus bonus) {
        return bonus.getHealingBonus();
    }

    @Override
    protected int getManaGainAmount(ResourceReplenishBonus bonus) {
        return bonus.getManaGainBonus();
    }
}
