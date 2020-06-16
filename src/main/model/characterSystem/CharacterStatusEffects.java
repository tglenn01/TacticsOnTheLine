package main.model.characterSystem;

import main.model.combatSystem.Ability;
import main.model.combatSystem.StatusEffect;

import java.util.LinkedList;
import java.util.List;

public class CharacterStatusEffects {
    private List<StatusEffect> statusEffectList;

    public CharacterStatusEffects() {
        statusEffectList = new LinkedList<>();
    }

    public void addStatusEffect(StatusEffect statusEffect) {
        statusEffectList.add(statusEffect);
    }

    public List<StatusEffect> getStatusEffects() {
        return this.statusEffectList;
    }

    public void updateStatusEffect(CharacterUnit activeUnit) {
        List<StatusEffect> toRemove = new LinkedList<>();
        for (StatusEffect statusEffect : statusEffectList) {
            int newDuration = statusEffect.getDuration();
            newDuration--;
            if (newDuration == 0) toRemove.add(statusEffect);
            else {
                statusEffect.setDuration(newDuration);
                System.out.println(statusEffect.getAbilityType() + " for " + newDuration + " more turns");
            }
        }
        for (StatusEffect endedStatusEffect : toRemove) {
            removeStatusEffect(activeUnit, endedStatusEffect);
            System.out.println(endedStatusEffect.getAbilityType() + " Has ended");
        }
    }

    // revert the stats that were gained/lost (if attack was gained remove the same amount of attack)
    private void removeStatusEffect(CharacterUnit activeUnit, StatusEffect endedStatusEffect) {
        Ability.AbilityType abilityType = endedStatusEffect.getAbilityType();
        StatSheet statSheet = activeUnit.getCharacterStatSheet();
        int amountChanged = endedStatusEffect.getAmountChanged();
        if (abilityType == Ability.AbilityType.ATTACK_BUFF) statSheet.removeStrength(amountChanged);
        else if (abilityType == Ability.AbilityType.ATTACK_DEBUFF) statSheet.addStrength(amountChanged);
        else if (abilityType == Ability.AbilityType.DEFENSE_BUFF) statSheet.removeArmour(amountChanged);
        else if (abilityType == Ability.AbilityType.DEFENSE_DEBUFF) statSheet.addArmour(amountChanged);
        statusEffectList.remove(endedStatusEffect);
    }
}
