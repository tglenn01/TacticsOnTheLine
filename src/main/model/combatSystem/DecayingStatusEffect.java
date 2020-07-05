package main.model.combatSystem;

public abstract class DecayingStatusEffect extends StatusEffect {
    protected int amountChanged;
    protected int duration;

    public DecayingStatusEffect(Ability.AbilityType abilityType, int amountChanged, int duration) {
        super(abilityType);
        this.amountChanged = amountChanged;
        this.duration = duration;
    }

    public int getAmountChanged() {
        return this.amountChanged;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int newDuration) {
        this.duration = newDuration;
    }
}
