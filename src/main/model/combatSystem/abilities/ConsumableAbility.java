package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.StatusEffect;
import main.model.itemSystem.Consumable;
import main.model.itemSystem.ConsumableItemInventory;
import main.model.itemSystem.ResourceReplenishBonus;

public class ConsumableAbility extends SupportiveAbility {

    public ConsumableAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                             AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect,  abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit)
            throws AttackMissedException, UnitIsDeadException {
        System.out.println("Must have an item");
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit, Consumable item) {
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (item.getAbilityType() == AbilityType.HEAL) {
            healUnit(receivingUnit, receivingUnitStatSheet, item);
        } if (item.getAbilityType() == AbilityType.MANA_GAIN) {
            gainMana(receivingUnit, receivingUnitStatSheet, item);
        } if (item.getAbilityType() == AbilityType.ATTACK_BUFF) {
            int amountChanged = buffAttack(receivingUnitStatSheet, item.getPotency());
            receivingUnit.getStatusEffects().addStatusEffect(new StatusEffect(AbilityType.ITEM, amountChanged, item.getDuration()));
        } if (item.getAbilityType() == AbilityType.DEFENSE_BUFF) {
            int amountChanged = buffDefense(receivingUnitStatSheet, item.getPotency());
            receivingUnit.getStatusEffects().addStatusEffect(new StatusEffect(AbilityType.ITEM, amountChanged, item.getDuration()));
        } if (item.getAbilityType() == AbilityType.ATTACK_DEBUFF) {
            int amountChanged = debuffAttack(receivingUnitStatSheet, item.getPotency());
            receivingUnit.getStatusEffects().addStatusEffect(new StatusEffect(AbilityType.ITEM, amountChanged, item.getDuration()));
        } if (item.getAbilityType() == AbilityType.DEFENSE_DEBUFF) {
            int amountChanged = debuffDefense(receivingUnitStatSheet, item.getPotency());
            receivingUnit.getStatusEffects().addStatusEffect(new StatusEffect(AbilityType.ITEM, amountChanged, item.getDuration()));
        }
        ConsumableItemInventory.getInstance().removeConsumableItem(item);
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
