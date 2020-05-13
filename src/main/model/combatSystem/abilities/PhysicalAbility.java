package main.model.combatSystem.abilities;

public class PhysicalAbility extends DamageAbility {

    public PhysicalAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                           AbilityType abilityType, int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, damage, accuracy, abilityDescription);
    }

    @Override
    protected int calculateDamage() {
        int damage = (activeUnitStatSheet.getStrength() + this.damage) - receivingUnitStatSheet.getArmour();
        if (damage < 0) damage = 0;
        return damage;
    }
}
