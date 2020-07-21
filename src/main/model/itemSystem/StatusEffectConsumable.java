package main.model.itemSystem;

public abstract class StatusEffectConsumable extends Consumable {
    protected int duration;

    public StatusEffectConsumable(String consumableName, int potency, int duration) {
        super(consumableName, potency);
        this.duration = duration;
    }
}
