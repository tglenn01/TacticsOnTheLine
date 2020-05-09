package main.model.combatSystem.abilities;

public class MagicAbility extends DamageAbility {

    public MagicAbility(String abilityName, int manaCost, int range, int damage, int areaOfEffect, double accuracy) {
        super(abilityName, manaCost, range, areaOfEffect, damage, accuracy);
    }

    @Override
    protected int calculateDamage() {
        return (activeUnitStatSheet.getMagic() + this.damage) - recivingUnitStatSheet.getResistance();
    }
}
