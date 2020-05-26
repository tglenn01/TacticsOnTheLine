package main.model.characterSystem;

import main.model.combatSystem.Ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterStatusEffects {
    protected Map<Ability.AbilityType, Integer> statusEffectDuration;


    public CharacterStatusEffects() {
        statusEffectDuration = new HashMap<>();
    }

    public void addStatusEffect(Ability.AbilityType statusEffect, int duration) {
        if (statusEffectDuration.containsKey(statusEffect)) {
            int value = statusEffectDuration.get(statusEffect);
            int newValue = value + duration;
            statusEffectDuration.put(statusEffect, newValue);
        } else statusEffectDuration.put(statusEffect, duration);
    }

    public void updateStatusEffect(CharacterUnit activeUnit) {
        List<Map.Entry<Ability.AbilityType, Integer>> toRemove = new ArrayList<>();
        for (Map.Entry<Ability.AbilityType, Integer> entry : statusEffectDuration.entrySet()) {
            int newDuration = entry.getValue();
            newDuration--;
            if (newDuration == 0) {
                toRemove.add(entry);
            } else {
                entry.setValue(newDuration);
                System.out.println(entry.getKey() + " for " + newDuration + " more turns");
            }
        }
        for (Map.Entry<Ability.AbilityType, Integer> entry : toRemove) {
            statusEffectDuration.remove(entry);
            System.out.println(entry.getKey() + " Has ended");
            removeStatusEffect(activeUnit, entry);
        }
    }

    private void removeStatusEffect(CharacterUnit activeUnit, Map.Entry<Ability.AbilityType, Integer> entry) {
        Ability.AbilityType statusEffect = entry.getKey();
        if (statusEffect == Ability.AbilityType.ATTACK_BUFF ||
                statusEffect == Ability.AbilityType.ATTACK_DEBUFF) {
            activeUnit.getCharacterStatSheet().revertStrength();
        } if (statusEffect == Ability.AbilityType.DEFENSE_BUFF ||
                statusEffect == Ability.AbilityType.DEFENSE_DEBUFF) {
            activeUnit.getCharacterStatSheet().revertArmour();
        }
        statusEffectDuration.remove(entry);
    }
}
