package main.model.combatSystem.abilities;

public class PhysicalAbility extends DamageAbility {

    public PhysicalAbility(String abilityName, int manaCost, int range, int areaOfEffect, int damage, double accuracy,
                           String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, damage, accuracy, abilityDescription);
    }

    @Override
    protected int calculateDamage() {
        return (activeUnitStatSheet.getStrength() + this.damage) - receivingUnitStatSheet.getArmour();
    }
}
