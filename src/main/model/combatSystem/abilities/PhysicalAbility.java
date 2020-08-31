package main.model.combatSystem.abilities;

import main.model.characterSystem.CharacterUnit;

public abstract class PhysicalAbility extends DamageAbility {

    public PhysicalAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                           int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, damage, accuracy, abilityDescription);
    }

    @Override
    public String getEffectType() {
        return "Physical";
    }

    @Override
    protected int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        int damage = (activeUnit.getCharacterStatSheet().getStrength() + this.damage) - receivingUnit.getCharacterStatSheet().getArmour();
        if (damage < 0) damage = 0;
        return damage;
    }
}
