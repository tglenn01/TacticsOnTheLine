package main.model.combatSystem;

import main.model.characterSystem.CharacterUnit;

public abstract class PermanentStatusEffect extends StatusEffect {
    protected int uses;

    public PermanentStatusEffect(CharacterUnit receivingUnit, int potency, int uses) {
        super(receivingUnit, potency);
        this.uses = uses;
    }

    public int getUses() {
        return this.uses;
    }

    public void setUses(int newUses) {
        this.uses = newUses;
    }
}
