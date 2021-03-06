package main.model.combatSystem;

import main.model.characterSystem.CharacterUnit;

public abstract class DecayingStatusEffect extends StatusEffect {
    protected int amountChanged;
    protected int duration;

    public DecayingStatusEffect(CharacterUnit receivingUnit, int potency, int duration) {
        super(receivingUnit, potency);
        this.duration = duration;
    }

    @Override
    protected void addStatusEffectToCharacterStatusEffects(CharacterUnit receivingUnit) {
        receivingUnit.getStatusEffects().addDecayingStatusEffect(this);
    }

    public int getAmountChanged() {
        return this.amountChanged;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getDurationInformation() {
        return this.duration;
    }

    public void setDuration(int newDuration) {
        this.duration = newDuration;
    }

    public abstract void removeStatusEffect(CharacterUnit receivingUnit);
}
