package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.itemSystem.ResourceReplenishBonus;

public class StatusEffectAbility extends SupportiveAbility {
    protected static int HEAL_CONSTANT = 10;

    public StatusEffectAbility(String abilityName, int manaCost, int range, int areaOfEffect, int duration,
                               AbilityType abilityType, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, duration, abilityType, abilityDescription);
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (this.abilityType == AbilityType.HEAL) {
            healUnit(receivingUnit, receivingUnitStatSheet, activeUnitStatSheet);
            return; // can remove this if I add the regen ability
        } if (this.abilityType == AbilityType.MANA_GAIN) {
            gainMana(receivingUnit, receivingUnitStatSheet, activeUnitStatSheet);
            return;
        } if (this.abilityType == AbilityType.ATTACK_BUFF) {
            buffAttack(receivingUnitStatSheet);
        } if (this.abilityType == AbilityType.DEFENSE_BUFF) {
            buffDefense(receivingUnitStatSheet);
        } if (this.abilityType == AbilityType.ATTACK_DEBUFF) {
            debuffAttack(receivingUnitStatSheet);
        } if (this.abilityType == AbilityType.DEFENSE_DEBUFF) {
            debuffDefense(receivingUnitStatSheet);
        }
        receivingUnit.getStatusEffects().addStatusEffect(this.abilityType, this.duration);
    }

    protected int getHealAmount(ResourceReplenishBonus bonus) {
        return bonus.getHealingBonus() + HEAL_CONSTANT;
    }

    @Override
    protected int getManaGainAmount(ResourceReplenishBonus bonus) {
        return bonus.getManaGainBonus();
    }
}
