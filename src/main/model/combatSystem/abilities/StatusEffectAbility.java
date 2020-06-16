package main.model.combatSystem.abilities;

import main.exception.AttackMissedException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.combatSystem.statusEffects.AttackBuff;
import main.model.combatSystem.statusEffects.AttackDebuff;
import main.model.combatSystem.statusEffects.DefenseBuff;
import main.model.combatSystem.statusEffects.DefenseDebuff;
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
        } if (this.abilityType == AbilityType.MANA_GAIN) {
            gainMana(receivingUnit, receivingUnitStatSheet, activeUnitStatSheet);
        } if (this.abilityType == AbilityType.ATTACK_BUFF) {
            int amountChanged = buffAttack(receivingUnitStatSheet, this.potency);
            receivingUnit.getStatusEffects().addStatusEffect(new AttackBuff(this.abilityType, this.potency, amountChanged));
        } if (this.abilityType == AbilityType.DEFENSE_BUFF) {
            int amountChanged = buffDefense(receivingUnitStatSheet, this.potency);
            receivingUnit.getStatusEffects().addStatusEffect(new DefenseBuff(this.abilityType, this.potency, amountChanged));
        } if (this.abilityType == AbilityType.ATTACK_DEBUFF) {
            int amountChanged = debuffAttack(receivingUnitStatSheet, this.potency);
            receivingUnit.getStatusEffects().addStatusEffect(new AttackDebuff(this.abilityType, this.potency, amountChanged));
        } if (this.abilityType == AbilityType.DEFENSE_DEBUFF) {
            int amountChanged = debuffDefense(receivingUnitStatSheet, this.potency);
            receivingUnit.getStatusEffects().addStatusEffect(new DefenseDebuff(this.abilityType, this.potency, amountChanged));
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
                this.abilityType == AbilityType.MANA_GAIN;
    }
}
