package main.model.itemSystem;

import main.model.characterSystem.CharacterUnit;

public abstract class Consumable {
    protected String consumableName;
    protected int potency;

    public Consumable(String consumableName, int potency) {
        this.consumableName = consumableName;
        this.potency = potency;
    }

    public String getConsumableName() {
        return consumableName;
    }

    public int getPotency() {
        return this.potency;
    }

    public abstract void applyItem(CharacterUnit receivingUnit);
}
