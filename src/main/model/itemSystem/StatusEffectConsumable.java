package main.model.itemSystem;

public abstract class StatusEffectConsumable extends Consumable {
    protected int duration;

    public StatusEffectConsumable(String consumableName, int potency, String description, int duration) {
        super(consumableName, potency, description);
        this.duration = duration;
    }
}
