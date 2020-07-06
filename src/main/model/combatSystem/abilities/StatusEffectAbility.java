package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.statusEffects.*;
import main.model.itemSystem.ResourceReplenishBonus;

public class StatusEffectAbility extends SupportiveAbility {
    protected static int HEAL_CONSTANT = 10;
    private int duration;
    private int potency;

    public StatusEffectAbility(String abilityName, int manaCost, int range, int areaOfEffect, int duration,
                               AbilityType abilityType, int potency, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, abilityDescription);
        this.duration = duration;
        this.potency = potency;
    }

    @Override
    public void takeAction(CharacterUnit activeUnit, CharacterUnit receivingUnit) throws AttackMissedException, UnitIsDeadException {
        if (isAreaOfEffect()) calculateAreaOfEffect(activeUnit, receivingUnit);
        else calculateSingleTarget(activeUnit, receivingUnit);
    }

    private void calculateSingleTarget(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        StatSheet receivingUnitStatSheet = receivingUnit.getCharacterStatSheet();
        if (this.abilityType == AbilityType.HEAL) {
            healUnit(receivingUnit, receivingUnitStatSheet, activeUnitStatSheet);
        } else if (this.abilityType == AbilityType.MANA_GAIN) {
            gainMana(receivingUnit, receivingUnitStatSheet, activeUnitStatSheet);
        } else if (this.abilityType == AbilityType.ATTACK_BUFF) {
            receivingUnit.getStatusEffects().addDecayingStatusEffect(new AttackBuff(receivingUnit, potency, duration));
        } else if (this.abilityType == AbilityType.DEFENSE_BUFF) {
            receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseBuff(receivingUnit, potency, duration));
        } else if (this.abilityType == AbilityType.ATTACK_DEBUFF) {
            receivingUnit.getStatusEffects().addDecayingStatusEffect(new AttackDebuff(receivingUnit, potency, duration));
        } else if (this.abilityType == AbilityType.DEFENSE_DEBUFF) {
            receivingUnit.getStatusEffects().addDecayingStatusEffect(new DefenseDebuff(receivingUnit, potency, duration));
        } else if (this.abilityType == AbilityType.INVULNERABLE) {
            receivingUnit.getStatusEffects().addPermanentStatusEffect(new Invulnerable(receivingUnit, potency));
        } else if (this.abilityType == AbilityType.ROOT) {
            receivingUnit.getStatusEffects().addDecayingStatusEffect(new Root(receivingUnit, potency, duration));
        }
    }

    private void calculateAreaOfEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        for (BoardSpace targetedBoardSpace : receivingUnit.getTargetedBoardSpacesForAreaOfEffect(this)) {
            if (targetedBoardSpace.isOccupied()) {
                calculateSingleTarget(activeUnit, targetedBoardSpace.getOccupyingUnit());
            }
        }
    }


    protected int getHealAmount(ResourceReplenishBonus bonus) {
        return bonus.getHealingBonus() + HEAL_CONSTANT;
    }

    @Override
    protected int getManaGainAmount(ResourceReplenishBonus bonus) {
        return bonus.getManaGainBonus();
    }

    public boolean targetsAlly() {
        return this.abilityType == Ability.AbilityType.HEAL ||
                this.abilityType == Ability.AbilityType.ATTACK_BUFF ||
                this.abilityType == Ability.AbilityType.DEFENSE_BUFF ||
                this.abilityType == Ability.AbilityType.ITEM ||
                this.abilityType == AbilityType.MANA_GAIN ||
                this.abilityType == AbilityType.INVULNERABLE;
    }
}
