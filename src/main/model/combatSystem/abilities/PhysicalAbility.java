package main.model.combatSystem.abilities;

public class PhysicalAbility extends DamageAbility {

    public PhysicalAbility(String abilityName, int manaCost, int range, int areaOfEffect, int damage, double accuracy) {
        super(abilityName, manaCost, range, areaOfEffect, damage, accuracy);
    }
}
