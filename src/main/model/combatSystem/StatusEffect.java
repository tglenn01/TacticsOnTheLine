package main.model.combatSystem;

public class StatusEffect {
    private Ability.AbilityType abilityType;
    private int amountChanged;
    private int duration;


    public StatusEffect(Ability.AbilityType abilityType, int amountChanged, int duration) {
        this.abilityType = abilityType;
        this.amountChanged = amountChanged;
        this.duration = duration;
    }

    public Ability.AbilityType getAbilityType() {
        return this.abilityType;
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
