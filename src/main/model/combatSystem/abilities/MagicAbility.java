package main.model.combatSystem.abilities;

public class MagicAbility extends DamageAbility {

    public MagicAbility(String abilityName, int manaCost, int range, int damage, int areaOfEffect, double accuracy,
                        String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, damage, accuracy, abilityDescription);
    }

    @Override
    protected int calculateDamage() {
        return (activeUnitStatSheet.getMagic() + this.damage) - receivingUnitStatSheet.getResistance();
    }
}
