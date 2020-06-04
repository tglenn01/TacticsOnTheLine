package main.model.combatSystem.abilities;

import main.model.characterSystem.CharacterUnit;

public class MagicAbility extends DamageAbility {

    public MagicAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                        AbilityType abilityType, int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, abilityType, damage, accuracy, abilityDescription);
    }

    @Override
    protected int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        int damage = (activeUnit.getCharacterStatSheet().getMagic() + this.damage)
                - receivingUnit.getCharacterStatSheet().getResistance();
        if (damage < 0) damage = 0;
        return damage;
    }
}
