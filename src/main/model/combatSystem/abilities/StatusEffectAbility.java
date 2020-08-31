package main.model.combatSystem.abilities;

public abstract class StatusEffectAbility extends SupportiveAbility {
    protected int duration;
    protected int potency;

    public StatusEffectAbility(String abilityName, int manaCost, int range, int duration, int areaOfEffect,
                               int potency, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityDescription);
        this.duration = duration;
        this.potency = potency;
    }

    @Override
    public String getEffectType() {
        return "Support";
    }
}
