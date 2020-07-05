package main.model.combatSystem;

public abstract class PermanentStatusEffect extends StatusEffect {
    protected int uses;

    public PermanentStatusEffect(Ability.AbilityType abilityType, int uses) {
        super(abilityType);
        this.uses = uses;
    }

    public int getUses() {
        return this.uses;
    }

    public void setUses(int newUses) {
        this.uses = newUses;
    }
}
