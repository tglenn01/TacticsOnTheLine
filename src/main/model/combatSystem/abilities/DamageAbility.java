package main.model.combatSystem.abilities;

import main.model.combatSystem.Ability;

public abstract class DamageAbility extends Ability {
    protected int damage;
    protected double accuracy;

    public DamageAbility(String abilityName, int manaCost, int range, int areaOfEffect, int damage, double accuracy) {
        super(abilityName, manaCost, range, areaOfEffect);
        this.damage = damage;
        this.accuracy = accuracy;
    }
}
