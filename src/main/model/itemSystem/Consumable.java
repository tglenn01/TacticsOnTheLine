package main.model.itemSystem;

import main.model.characterSystem.CharacterUnit;

public abstract class Consumable {
    protected String consumableName;
    protected int potency;
    protected String description;

    public Consumable(String consumableName, int potency, String description) {
        this.consumableName = consumableName;
        this.potency = potency;
        this.description = description;
    }

    public String getConsumableName() {
        return consumableName;
    }

    public int getPotency() {
        return this.potency;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract void applyItem(CharacterUnit receivingUnit);
}
