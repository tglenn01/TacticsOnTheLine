package main.model.combatSystem.abilities;

public class MagicAbility extends DamageAbility {

    public MagicAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                        AbilityType abilityType, int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, damage, accuracy, abilityDescription);
    }

    @Override
    protected int calculateDamage() {
        return (activeUnitStatSheet.getMagic() + this.damage) - receivingUnitStatSheet.getResistance();
    }
}
