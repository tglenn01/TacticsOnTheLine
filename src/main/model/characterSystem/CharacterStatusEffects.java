package main.model.characterSystem;

import main.model.combatSystem.Ability;
import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.PermanentStatusEffect;

import java.util.LinkedList;
import java.util.List;

public class CharacterStatusEffects {
    private List<DecayingStatusEffect> decayingStatusEffects;
    private List<PermanentStatusEffect> permanentStatusEffects;

    public CharacterStatusEffects() {
        decayingStatusEffects = new LinkedList<>();
        permanentStatusEffects = new LinkedList<>();
    }

    public void addDecayingStatusEffect(DecayingStatusEffect decayingStatusEffect) {
        decayingStatusEffects.add(decayingStatusEffect);
    }

    public void addPermanentStatusEffect(PermanentStatusEffect permanentStatusEffect) {
        permanentStatusEffects.add(permanentStatusEffect);
    }

    public List<DecayingStatusEffect> getDecayingStatusEffects() {
        return this.decayingStatusEffects;
    }

    // remove 1 from each decayingStatusEffect, remove if it hits 0
    public void updateStatusEffect(CharacterUnit activeUnit) {
        List<DecayingStatusEffect> toRemove = new LinkedList<>();
        for (DecayingStatusEffect statusEffect : decayingStatusEffects) {
            int newDuration = statusEffect.getDuration();
            newDuration--;
            if (newDuration == 0) toRemove.add(statusEffect);
            else {
                statusEffect.setDuration(newDuration);
                System.out.println(statusEffect.getAbilityType() + " for " + newDuration + " more turns");
            }
        }
        for (DecayingStatusEffect endedStatusEffect : toRemove) {
            removeStatusEffect(activeUnit, endedStatusEffect);
            System.out.println(endedStatusEffect.getAbilityType() + " Has ended");
        }
    }

    // revert the stats that were gained/lost (if attack was gained remove the same amount of attack)
    private void removeStatusEffect(CharacterUnit activeUnit, DecayingStatusEffect endedStatusEffect) {
        Ability.AbilityType abilityType = endedStatusEffect.getAbilityType();
        StatSheet statSheet = activeUnit.getCharacterStatSheet();
        int amountChanged = endedStatusEffect.getAmountChanged();
        if (abilityType == Ability.AbilityType.ATTACK_BUFF) statSheet.removeStrength(amountChanged);
        else if (abilityType == Ability.AbilityType.ATTACK_DEBUFF) statSheet.addStrength(amountChanged);
        else if (abilityType == Ability.AbilityType.DEFENSE_BUFF) statSheet.removeArmour(amountChanged);
        else if (abilityType == Ability.AbilityType.DEFENSE_DEBUFF) statSheet.addArmour(amountChanged);
        decayingStatusEffects.remove(endedStatusEffect);
    }

    public boolean hasInvulnerable() {
        for (PermanentStatusEffect statusEffect : permanentStatusEffects) {
            if (statusEffect.getAbilityType() == Ability.AbilityType.INVULNERABLE) return true;
        }
        return false;
    }

    public void removeInvulnerable() {
        PermanentStatusEffect statusEffect = null;
        for (PermanentStatusEffect iterator : permanentStatusEffects) {
            if (iterator.getAbilityType() == Ability.AbilityType.INVULNERABLE) {
                statusEffect = iterator;
            }
        }
        assert statusEffect != null; // calling this method means one has to have been invulnerable
        int oldUses = statusEffect.getUses();
        oldUses--;
        if (oldUses <= 0) {
            permanentStatusEffects.remove(statusEffect);
        }
    }

    public boolean isRooted() {
        for (DecayingStatusEffect decayingStatusEffect : decayingStatusEffects) {
            if (decayingStatusEffect.getAbilityType() == Ability.AbilityType.ROOT) return true;
        }
        return false;
    }
}
