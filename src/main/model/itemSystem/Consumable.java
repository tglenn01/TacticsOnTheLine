package main.model.itemSystem;

import main.model.combatSystem.Ability;

public class Consumable implements ResourceReplenishBonus {
    private String consumableName;
    private int potency;
    private int duration;
    private Ability.AbilityType abilityType;

    public Consumable(String consumableName, int potency, int duration, Ability.AbilityType abilityType) {
        this.consumableName = consumableName;
        this.potency = potency;
        this.duration = duration;
        this.abilityType = abilityType;
    }

    @Override
    public int getHealingBonus() {
        return potency;
    }

    @Override
    public int getManaGainBonus() { return potency; }

    public String getConsumableName() {
        return consumableName;
    }

    public int getPotency() {
        return this.potency;
    }

    public int getDuration() {
        return duration;
    }

    public Ability.AbilityType getAbilityType() {
        return abilityType;
    }
}
