package main.model.characterSystem;

import main.model.combatSystem.DecayingStatusEffect;
import main.model.combatSystem.PermanentStatusEffect;
import main.model.combatSystem.StatusEffect;
import main.model.combatSystem.statusEffects.Invulnerable;
import main.model.combatSystem.statusEffects.Root;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CharacterStatusEffects implements Iterable<StatusEffect> {
    private List<DecayingStatusEffect> decayingStatusEffects;
    private List<PermanentStatusEffect> permanentStatusEffects;

    public CharacterStatusEffects() {
        decayingStatusEffects = new LinkedList<>();
        permanentStatusEffects = new LinkedList<>();
    }

    public void addDecayingStatusEffect(DecayingStatusEffect decayingStatusEffect) {
        decayingStatusEffects.add(decayingStatusEffect);
    }

    public void addPermanentStatusEffect(PermanentStatusEffect newEffect) {
        boolean alreadyHasStatusEffect = false;

        for (PermanentStatusEffect currentEffect : permanentStatusEffects) {
            if (currentEffect.getClass() == newEffect.getClass()) {
                alreadyHasStatusEffect = true;
                int currentUses = currentEffect.getUses();
                currentEffect.setUses(currentUses + newEffect.getUses());
            }
        }

        if (!alreadyHasStatusEffect) permanentStatusEffects.add(newEffect);
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
                System.out.println(statusEffect.getCondensedName() + " for " + newDuration + " more turns");
            }
        }
        for (DecayingStatusEffect endedStatusEffect : toRemove) {
            endedStatusEffect.removeStatusEffect(activeUnit);
            decayingStatusEffects.remove(endedStatusEffect);
            System.out.println(endedStatusEffect.getCondensedName() + " Has ended");
        }
    }

    public boolean hasInvulnerable() {
        for (PermanentStatusEffect statusEffect : permanentStatusEffects) {
            if (statusEffect.getClass() == Invulnerable.class) return true;
        }
        return false;
    }

    public void removePermanentStatusEffect(PermanentStatusEffect permanentStatusEffect) {
        for (PermanentStatusEffect statusEffect : permanentStatusEffects) {
            if (statusEffect == permanentStatusEffect) {
                int oldUses = statusEffect.getUses();
                oldUses--;
                if (oldUses <= 0) {
                    permanentStatusEffects.remove(statusEffect);
                }
            }
        }
    }

    public boolean isRooted() {
        for (DecayingStatusEffect decayingStatusEffect : decayingStatusEffects) {
            if (decayingStatusEffect.getClass() == Root.class) return true;
        }
        return false;
    }

    public PermanentStatusEffect getInvulnerable() {
        for (PermanentStatusEffect permanentStatusEffect : permanentStatusEffects) {
            if (permanentStatusEffect.getClass() == Invulnerable.class) return permanentStatusEffect;
        }
        return null;
    }


    @Override
    public Iterator<StatusEffect> iterator() {
        return new StatusEffectIterator();
    }

    private class StatusEffectIterator implements Iterator<StatusEffect> {
        private int cursor = 0;
        private int roof = decayingStatusEffects.size() + permanentStatusEffects.size();

        @Override
        public boolean hasNext() {
            return cursor < roof;
        }

        @Override
        public StatusEffect next() {
            StatusEffect statusEffect;
            int midPoint = decayingStatusEffects.size();

            if (cursor < midPoint) statusEffect = decayingStatusEffects.get(cursor);
            else statusEffect = permanentStatusEffects.get(cursor - midPoint);
            cursor++;
            return statusEffect;
        }
    }
}
