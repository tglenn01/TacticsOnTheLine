package main.model.combatSystem.abilities;

import main.model.characterSystem.CharacterUnit;

public abstract class MagicAbility extends DamageAbility {

    public MagicAbility(String abilityName, int manaCost, int range, int areaOfEffect,
                        int damage, double accuracy, String abilityDescription) {
        super(abilityName, manaCost, range, areaOfEffect, damage, accuracy, abilityDescription);
    }

    @Override
    public String getEffectType() {
        return "Magic Damage";
    }

    @Override
    protected int calculateDamage(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        int damage = (activeUnit.getCharacterStatSheet().getMagic() + this.damage)
                - receivingUnit.getCharacterStatSheet().getResistance();
        if (damage < 0) damage = 0;
        return damage;
    }

    @Override
    protected void applyAdditionalEffects(CharacterUnit activeUnit, CharacterUnit receivingUnit) {
        // base magic abilities don't apply addition effect
    }
}
